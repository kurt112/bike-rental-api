package com.thesis.bikerental.utils.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ServiceGraphQL<T> {
    Set<T> data(String search, int page, int status);

    T save(T t);

    boolean deleteById(String id);

    T findById(String id);

    ApiSettings apiSettings();

    long count();
}
