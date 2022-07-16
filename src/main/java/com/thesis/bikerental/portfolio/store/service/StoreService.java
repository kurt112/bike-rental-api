package com.thesis.bikerental.portfolio.store.service;

import com.thesis.bikerental.portfolio.store.domain.Store;
import com.thesis.bikerental.utils.api.ServiceGraphQL;
import org.springframework.stereotype.Service;

@Service
public interface StoreService extends ServiceGraphQL<Store> {
}
