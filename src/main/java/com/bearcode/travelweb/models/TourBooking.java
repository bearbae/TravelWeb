package com.bearcode.travelweb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@Entity
@Data
public class TourBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private int checkBrowser;

    public TourBooking() {
        this.checkBrowser = 0;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;
}
