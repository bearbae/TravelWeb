package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.BookingRequestDTO;
import org.springframework.http.ResponseEntity;

public interface HotelBookingService {
    ResponseEntity<String> createBooking( BookingRequestDTO bookingRequest);
    String deleteBooking(Long id) ;
    ResponseEntity<String> setCheck(Long id);

}
