package com.thesis.bikerental.utils.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApiSettings {
    private long totalElements;
    private int totalPages;
    private int currentPage;
}
