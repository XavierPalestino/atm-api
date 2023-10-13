package com.xavier.atmdemo.service;

import com.xavier.atmdemo.dto.AtmDTO;
import com.xavier.atmdemo.entity.Balance;
import com.xavier.atmdemo.entity.Billing;
import com.xavier.atmdemo.form.AtmForm;
import com.xavier.atmdemo.repository.BalanceRepository;
import com.xavier.atmdemo.repository.BillingRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class WithdrawService {

  private final BillingRepository billingRepository;
  private final BalanceRepository balanceRepository;

  public AtmDTO withdraw(final AtmForm atmForm) {
    final Map<Double, Integer> billingInAtm = this.setBilling();
    final Map<Double, Integer> billingToRetrieve = this.setBilling(billingInAtm);

    final Balance balanceInAtm = this.getAtmBalance(atmForm.getAtmCode());

    double previousImportType = this.findClosestNumber(billingInAtm, atmForm.getMoneyToWithdraw());
    double moneyToRetrieve = atmForm.getMoneyToWithdraw();

    return this.withdrawProcess(
        billingInAtm, billingToRetrieve, balanceInAtm, moneyToRetrieve, previousImportType);
  }

  public AtmDTO withdrawProcess(
      final Map<Double, Integer> billingInAtm,
      final Map<Double, Integer> billingToRetrieve,
      final Balance balanceInAtm,
      double moneyToRetrieve,
      double previousImportType) {

    double moneyPendingToRetrieve = moneyToRetrieve;
    double moneyRetrieved = 0;
    int counter = 0;

    do {
      final double reduce = this.findClosestNumber(billingInAtm, moneyPendingToRetrieve);
      final Billing billing = this.importTypeExistence(reduce);
      if (billing.getBill() > 0) {
        moneyRetrieved += reduce;
        moneyPendingToRetrieve -= reduce;
        counter++;
        balanceInAtm.setAtmBalance(balanceInAtm.getAtmBalance() - reduce);
        billing.setBill(billing.getBill() - 1);
        this.billingRepository.save(billing);
        billingToRetrieve.replace(previousImportType, counter);
        if (previousImportType != reduce) {
          System.out.println("bill: " + previousImportType + "import: " + counter);
          previousImportType = reduce;
          counter = 0;
        }

      } else billingInAtm.remove(reduce);

    } while (moneyRetrieved < moneyToRetrieve);

    this.balanceRepository.save(balanceInAtm);

    return AtmDTO.builder()
        .balanceInAtm(balanceInAtm.getAtmBalance())
        .billingsToRetrieve(billingToRetrieve)
        .build();
  }

  public Map<Double, Integer> setBilling() {
    final Map<Double, Integer> billToWithdraw = new HashMap<>();
    List<Billing> billings = this.billingRepository.findAll();

    billings.forEach((bill) -> billToWithdraw.put(bill.getImportType(), bill.getBill()));

    return billToWithdraw;
  }

  public Map<Double, Integer> setBilling(final Map<Double, Integer> billingInAtm) {
    final Map<Double, Integer> billToWithdraw = new HashMap<>();

    billingInAtm.forEach(
        (importType, bill) -> {
          billToWithdraw.put(importType, 0);
        });

    return billToWithdraw;
  }

  public Balance getAtmBalance(final String atmCode) {
    return balanceRepository.getAllByAtmCode(atmCode);
  }

  private Double findClosestNumber(
      final Map<Double, Integer> billing, final double moneyToWithdraw) {
    final double[] minDiference = {-1, Integer.MAX_VALUE};

    billing.forEach(
        (importType, bill) -> {
          double diference = moneyToWithdraw - importType;

          if (diference >= 0 && diference < minDiference[1]) {
            minDiference[0] = importType;
            minDiference[1] = diference;
          }
        });

    return minDiference[0];
  }

  public Billing importTypeExistence(final double importType) {
    return this.billingRepository.findBillingByImportType(importType);
  }
}
