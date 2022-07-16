package com.thesis.bikerental.portfolio.receipt.service;

import com.thesis.bikerental.portfolio.receipt.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
}
