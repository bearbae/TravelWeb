package com.bearcode.travelweb.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class RestaurantBookingResponseDTO {

    private Long idBook;
    private LocalDate checkInDate;
    private BigDecimal totalAmount;
    private String restaurantName;
    private Long restaurantId;
    private String nameUser ;
    private String email;
    private String phoneNumber;
    private String specialTable;
    private int checkBrowser;
    private String tableRestaurantName;
    private Integer tableQuantity;

    public RestaurantBookingResponseDTO(Long idBook, LocalDate checkInDate, BigDecimal totalAmount, String restaurantName, Long restaurantId, int checkBrowser,String tableRestaurantName, Integer tableQuantity) {
        this.idBook = idBook;
        this.checkInDate = checkInDate;
        this.totalAmount = totalAmount;
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.checkBrowser = checkBrowser;
        this.tableRestaurantName = tableRestaurantName;
        this.tableQuantity = tableQuantity;
    }

    public RestaurantBookingResponseDTO(Long idBook, LocalDate checkInDate, BigDecimal totalAmount, String restaurantName, Long restaurantId, String nameUser, String email, String phoneNumber, String specialTable, int checkBrowser, String tableRestaurantName, Integer tableQuantity) {
        this.idBook = idBook;
        this.checkInDate = checkInDate;
        this.totalAmount = totalAmount;
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.nameUser = nameUser;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialTable = specialTable;
        this.checkBrowser = checkBrowser ;
        this.tableRestaurantName = tableRestaurantName;
        this.tableQuantity = tableQuantity;
    }
}
