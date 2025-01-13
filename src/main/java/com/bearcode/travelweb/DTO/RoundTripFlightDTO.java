package com.bearcode.travelweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundTripFlightDTO {
    private String departureFlightNumber;
    private String returnFlightNumber;
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private LocalTime departureTime;
    private LocalTime returnTime;
    private BigDecimal price;
    private int remainingSeatsForDeparture;
    private int remainingSeatsForReturn;
}
