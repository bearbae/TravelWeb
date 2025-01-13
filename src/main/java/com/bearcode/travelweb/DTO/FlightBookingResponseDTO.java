package com.bearcode.travelweb.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FlightBookingResponseDTO {
    private Long idBook;
    private LocalDate departureDateBook;
    private LocalDate arrivalDateBook;
    private BigDecimal totalAmount;
    private String nameDepartureCity;
    private String nameArrivalCity;
    private Long flightId;
    private String nameUser;
    private String email;
    private String phoneNumber;
    private int checkBrowser;

    public FlightBookingResponseDTO(Long idBook, LocalDate departureDateBook, LocalDate arrivalDateBook, BigDecimal totalAmount,
                                    String nameDepartureCity, String nameArrivalCity, Long flightId, int checkBrowser) {
        this.idBook = idBook;
        this.departureDateBook = departureDateBook;
        this.arrivalDateBook = arrivalDateBook;
        this.totalAmount = totalAmount;
        this.nameDepartureCity = nameDepartureCity;
        this.nameArrivalCity = nameArrivalCity;
        this.flightId = flightId;
        this.checkBrowser = checkBrowser;
    }

    public FlightBookingResponseDTO(Long idBook, LocalDate departureDateBook, LocalDate arrivalDateBook, BigDecimal totalAmount,
                                    String nameDepartureCity, String nameArrivalCity, Long flightId, String nameUser,
                                    String email, String phoneNumber, int checkBrowser) {
        this.idBook = idBook;
        this.departureDateBook = departureDateBook;
        this.arrivalDateBook = arrivalDateBook;
        this.totalAmount = totalAmount;
        this.nameDepartureCity = nameDepartureCity;
        this.nameArrivalCity = nameArrivalCity;
        this.flightId = flightId;
        this.nameUser = nameUser;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.checkBrowser = checkBrowser;
    }
}
