package com.thesis.bikerental.portfolio.store.service;

import com.thesis.bikerental.portfolio.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
