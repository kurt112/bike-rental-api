package com.thesis.bikerental.utils.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PagesContent <T>{
    private List<T> items;
    private long totalItems;
    private int totalPages;
    private int currentPage;
}
