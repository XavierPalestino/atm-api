package com.xavier.atmdemo.repository;

import com.xavier.atmdemo.entity.Balance;
import jakarta.validation.constraints.Min;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository
    extends JpaRepository<Balance, Integer>, JpaSpecificationExecutor<Balance> {

  Balance getAllByAtmCode(@Min(1) String atmCode);
}
