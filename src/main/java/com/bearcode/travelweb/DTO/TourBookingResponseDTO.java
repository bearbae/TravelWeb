package com.bearcode.travelweb.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TourBookingResponseDTO {
    private Long idBookTour ;
    private LocalDate dateBook ;
    private BigDecimal totalAmount ;
    private String nameTour ;
    private Long idTour;
    private String nameUser;
    private String email;
    private String phoneNumber;
    private String specialRequest;
    private int checkBrowser ;

    public TourBookingResponseDTO(Long idBookTour, LocalDate dateBook, BigDecimal totalAmount, String nameTour, Long idTour, int checkBrowser) {
        this.idBookTour = idBookTour;
        this.dateBook = dateBook;
        this.totalAmount = totalAmount;
        this.nameTour = nameTour;
        this.idTour = idTour ;
        this.checkBrowser = checkBrowser;
    }

    public TourBookingResponseDTO(Long idBookTour, LocalDate dateBook, BigDecimal totalAmount, String nameTour, Long idTour, String nameUser, String email, String phoneNumber, String specialRequest, int checkBrowser) {
        this.idBookTour = idBookTour;
        this.dateBook = dateBook;
        this.totalAmount = totalAmount;
        this.nameTour = nameTour;
        this.idTour = idTour;
        this.nameUser = nameUser;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialRequest = specialRequest;
        this.checkBrowser = checkBrowser;

    }
}
