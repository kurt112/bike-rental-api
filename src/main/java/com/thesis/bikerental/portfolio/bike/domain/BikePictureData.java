
package com.thesis.bikerental.portfolio.bike.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BikePictureData {
    private long id;
    private String  blob;
}
