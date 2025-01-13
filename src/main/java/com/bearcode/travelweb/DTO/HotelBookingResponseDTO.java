package com.bearcode.travelweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class HotelBookingResponseDTO {

    private Long idBook;
    private LocalDate checkInDate;
    private BigDecimal totalAmount;
    private String hotelName;
    private Long hotelId;
    private String nameUser ;
    private String email;
    private String phoneNumber;
    private String specialRequest;
    private int checkBrowser;
    private String roomName;
    private Integer roomQuantity;

    public HotelBookingResponseDTO(Long idBook, LocalDate checkInDate, BigDecimal totalAmount, String hotelName, Long hotelId, int checkBrowser, String roomName, Integer roomQuantity) {
        this.idBook = idBook;
        this.checkInDate = checkInDate;
        this.totalAmount = totalAmount;
        this.hotelName = hotelName;
        this.hotelId = hotelId;
        this.checkBrowser = checkBrowser;
        this.roomName = roomName;
        this.roomQuantity = roomQuantity;
    }

    public HotelBookingResponseDTO(Long idBook, LocalDate checkInDate, BigDecimal totalAmount, String hotelName, Long hotelId, String nameUser, String email, String phoneNumber, String specialRequest, int checkBrowser, String roomName, Integer roomQuantity) {
        this.idBook = idBook;
        this.checkInDate = checkInDate;
        this.totalAmount = totalAmount;
        this.hotelName = hotelName;
        this.hotelId = hotelId;
        this.nameUser = nameUser;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialRequest = specialRequest;
        this.checkBrowser = checkBrowser ;
        this.roomName = roomName;
        this.roomQuantity = roomQuantity;
    }
}
