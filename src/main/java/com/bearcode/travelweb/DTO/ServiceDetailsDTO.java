package com.bearcode.travelweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDetailsDTO {
    private LocalTime timeToGo;
    private LocalTime timeToBack;
    private LocalTime time1;
    private String address1;
    private LocalTime time2;
    private String address2;
    private LocalTime time3;
    private String address3;
    private String vehicle;
    private BigDecimal pricePerAdult;
    private BigDecimal pricePerChild;
    private String code;
}
