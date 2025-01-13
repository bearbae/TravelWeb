package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.TourBookingDTO;
import com.bearcode.travelweb.models.TourBooking;
import org.springframework.http.ResponseEntity;

public interface TourBookingService {
    ResponseEntity<TourBooking> createNewBooking(TourBookingDTO tourBookingDTO);
    String deleteBooking(Long id) ;
    ResponseEntity<String> setCheck(Long id) ;
}
