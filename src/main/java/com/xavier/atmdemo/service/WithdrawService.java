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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class WithdrawService {

  private final BillingRepository billingRepository;
  private final BalanceRepository balanceRepository;

  public AtmDTO withdraw(final AtmForm atmForm) {
    return withdrawCash(Math.abs(atmForm.getMoneyToWithdraw()), atmForm.getAtmCode());
  }

  public AtmDTO withdrawCash(final double amount, final String atmCode) {
    List<Billing> cash = billingRepository.findAll(Sort.by(Sort.Order.desc("importType")));
    final Balance balanceInAtm = this.getAtmBalance(atmCode);
    Map<Double, Integer> cashToRetrieve = new HashMap<>();
    double amountRemaining = amount;
    int amountWithoutCents = (int) amount;

    if (amountRemaining - amountWithoutCents == 0.50 || amountRemaining - amountWithoutCents == 0.00) {

      for (Billing bill : cash) {
        if (amountRemaining <= 0) {
          break;
        }

        double billValue = bill.getImportType();
        int billAvailable = bill.getBill();
        double amountToRetrieve = amountRemaining / billValue;

        if (amountToRetrieve > 0 && billAvailable > 0) {
          int billToRetrieve = (int) Math.min(amountToRetrieve, billAvailable);
          cashToRetrieve.put(billValue, billToRetrieve);
          amountRemaining -= billValue * billToRetrieve;
          bill.setBill(billAvailable - billToRetrieve);
          billingRepository.save(bill);
        }
      }
    } else
      throw new RuntimeException("Solo contamos con importes de 0.50 centavos");

    if (amountRemaining > 0) {
      throw new RuntimeException("No es posible proporcionar el monto solicitado con los billetes disponibles.");
    }
    balanceInAtm.setAtmBalance(balanceInAtm.getAtmBalance() - amount);

    balanceRepository.save(balanceInAtm);

    return AtmDTO.builder().billingsToRetrieve(cashToRetrieve).balanceInAtm(balanceInAtm.getAtmBalance()).build();
  }

  public Balance getAtmBalance(final String atmCode) {
    return balanceRepository.getAllByAtmCode(atmCode);
  }

}
