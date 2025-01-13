package com.bearcode.travelweb.services;


import com.bearcode.travelweb.DTO.FlightDTO;
import com.bearcode.travelweb.DTO.RoundTripFlightDTO;
import com.bearcode.travelweb.models.Flight;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

public interface FlightService {
    String addFlight(FlightDTO flightDTO);
    // lấy danh sách chuyến bay
    ArrayList<Flight> getAllFlight();

    ArrayList<Flight> findAllFlightBySearch(String departureCity, String arrivalCity, LocalDate departureDate);

    // sửa thông tin chuyến bay
    FlightDTO updateFlight(Long id, FlightDTO flightDTO);

    // xóa thông tin chuyến bay
    String deleteFlight(Long id);

    // Lấy thông tin 1 chuyến bay
    Flight findFlightById(Long id);

    ArrayList<RoundTripFlightDTO> searchAllRoundTripFlights();

    ArrayList<RoundTripFlightDTO> searchRoundTripFlights(String departureCity, String arrivalCity, LocalDate departureDate, LocalDate returnDate);

}
