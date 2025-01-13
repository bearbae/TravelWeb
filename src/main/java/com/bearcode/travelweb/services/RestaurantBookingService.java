package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.BookingTableRestaurantDTO;
import com.bearcode.travelweb.models.HotelBooking;
import com.bearcode.travelweb.models.RestaurantBooking;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantBookingService {
    ResponseEntity<String> createBookingTableRestaurant( BookingTableRestaurantDTO bookingTableRestaurant);
    String deleteBooking(Long id) ;
    ResponseEntity<String> setCheck(Long id);
}
