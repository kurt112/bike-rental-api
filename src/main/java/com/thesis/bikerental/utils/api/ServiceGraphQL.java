package com.thesis.bikerental.utils.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ServiceGraphQL<T> {
    List<T> data(String search, int page, int size, int status);

    T save(T t);

    boolean deleteById(long id);

    T findById(long id);

    ApiSettings apiSettings();

    long count();
}
