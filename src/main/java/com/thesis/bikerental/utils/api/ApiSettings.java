package com.thesis.bikerental.utils.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ApiSettings {
    private long totalElements;
    private long totalPages;
    private long currentPage;
    private long maxElement;
    private long minElement;

    public void initApiSettings(long size, long page, long pageTotal, long totalElements){
        this.maxElement = size * page;
        this.totalPages = pageTotal;
        this.totalElements  = totalElements;
        this.currentPage = page;
        if(maxElement >= totalElements) this.maxElement = totalElements;

        this.minElement = this.maxElement - size + 1;

        if(totalElements <=0) this.minElement =0;
    }
}
