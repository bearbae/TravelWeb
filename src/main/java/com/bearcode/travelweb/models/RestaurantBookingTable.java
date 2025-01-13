package com.bearcode.travelweb.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantBookingTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_booking_id")
    private RestaurantBooking restaurantBooking;

    @ManyToOne
    @JoinColumn(name = "tableRestaurant_id")
    private TableRestaurant tableRestaurant;

    private int quantity;
}
