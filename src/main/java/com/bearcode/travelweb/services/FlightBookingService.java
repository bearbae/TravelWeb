package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.FlightBookingRequestDTO;
import org.springframework.http.ResponseEntity;

public interface FlightBookingService {
    ResponseEntity<String> createBooking(FlightBookingRequestDTO bookingRequest);

    String deleteBooking(Long id) ;
    ResponseEntity<String> setCheck(Long id);
}