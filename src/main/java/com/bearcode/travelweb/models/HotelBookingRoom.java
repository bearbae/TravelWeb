package com.bearcode.travelweb.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelBookingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_booking_id")
    private HotelBooking hotelBooking;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private int quantity;
}
