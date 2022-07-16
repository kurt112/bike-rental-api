package com.thesis.bikerental.portfolio.store.service;

import com.thesis.bikerental.portfolio.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
