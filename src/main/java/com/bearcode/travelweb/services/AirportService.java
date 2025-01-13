package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.AirportDTO;
import com.bearcode.travelweb.models.Airport;

import java.util.List;

public interface AirportService {
    String addAirport(AirportDTO airportDTO);
    List<Airport> getAllAirports();
    List<Airport> searchAirportsByName(String name);
    AirportDTO updateAirport(Long id, AirportDTO airportDTO);
    String deleteAirport(Long id);
    Airport findAirportById(Long id);
}
