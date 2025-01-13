package com.bearcode.travelweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDTO {
    private String departureAirport; // Sân bay cất cánh
    private String arrivalAirport; // Sân bay hạ cánh
    private String routeCode; // Mã chặng bay
}
