package com.bearcode.travelweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDTO {
    private String typeTour;
    private String address;
    private String titleTour;
    private Double rateStar;
    private int quantityComment;
    private int booked;
    private BigDecimal price;
    private String highlight;
    private String content;
}
