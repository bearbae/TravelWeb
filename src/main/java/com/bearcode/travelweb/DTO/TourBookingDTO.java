package com.bearcode.travelweb.DTO;

import com.bearcode.travelweb.models.Tour;
import com.bearcode.travelweb.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourBookingDTO {
    private LocalDate dateBook;
    private String tourDuration;
    private String typeService;
    private String hdv ;
    private int quantityAdults;
    private int quantityChildren;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String specialRequest;
    private BigDecimal totalAmount;
    private User user;
    private Tour tour;
}
