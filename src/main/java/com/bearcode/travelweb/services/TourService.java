package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.TourDTO;
import com.bearcode.travelweb.models.Tour;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface TourService {


    ResponseEntity<Tour> addTour(TourDTO tourDTO);

    // xóa tour
    String deleteTour(Long idTour);
    // cap nhat tour
    Tour updateTour(Long idTour, TourDTO tourDTO);
}
