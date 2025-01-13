package com.bearcode.travelweb.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNumber ;
    private String departureCity ;
    private String arrivalCity ;
    private LocalDate departureDate ;
    private LocalDate arrivalDate ;
    private LocalTime departureTime ;
    private LocalTime arrivalTime ;
    private BigDecimal price ;
    private int availableSeats ;
//    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "flight", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<FlightBooking> flightBookings;

}
