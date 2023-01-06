package com.thesis.bikerental.portfolio.charge.service;

import com.thesis.bikerental.portfolio.charge.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
