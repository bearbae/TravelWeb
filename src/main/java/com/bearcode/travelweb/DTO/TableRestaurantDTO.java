package com.bearcode.travelweb.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableRestaurantDTO {
    private Long id;
    private String tableRestaurantName;
    private String tableRestaurantType;
    private int area;
    private BigDecimal price;
    private String amenities;
    private String imageTable;
    private int quantity;
    private Long restaurantId;
}
