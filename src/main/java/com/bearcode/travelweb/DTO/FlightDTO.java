package com.bearcode.travelweb.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private String flightNumber ;
    private String departureCity ;
    private String arrivalCity ;
    private LocalDate departureDate ;
    private LocalDate arrivalDate ;
    private LocalTime departureTime ;
    private LocalTime arrivalTime ;
    private BigDecimal price ;
    private int availableSeats ;
}
