package com.bearcode.travelweb.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class HotelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkInDate;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String specialRequest;
    private BigDecimal totalAmount;
    private int checkBrowser;
    public HotelBooking() {
        this.checkBrowser = 0;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "hotelBooking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelBookingRoom> hotelBookingRooms;
}
