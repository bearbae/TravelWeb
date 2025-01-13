package com.bearcode.travelweb.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long id;
    private String roomName;
    private String roomType;
    private String view;
    private int area;
    private BigDecimal pricePerNight;
    private String amenities;
    private String imageRoom;
    private int quantity;
    private Long hotelId;
}
