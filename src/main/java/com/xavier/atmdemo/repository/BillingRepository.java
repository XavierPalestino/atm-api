package com.xavier.atmdemo.repository;

import com.xavier.atmdemo.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository
    extends JpaRepository<Billing, Integer>, JpaSpecificationExecutor<Billing> {

}
