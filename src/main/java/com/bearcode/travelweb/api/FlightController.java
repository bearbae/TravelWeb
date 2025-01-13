package com.bearcode.travelweb.api;


import com.bearcode.travelweb.DTO.FlightDTO;
import com.bearcode.travelweb.DTO.RoundTripFlightDTO;
import com.bearcode.travelweb.models.Flight;
import com.bearcode.travelweb.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/admin/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping("/add")
    public ResponseEntity<String> addFlight(@RequestBody FlightDTO flightDTO){
        String response = flightService.addFlight(flightDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ArrayList<Flight> getAllFlight(){
        return flightService.getAllFlight();
    }

    @GetMapping("/search")
    public ResponseEntity<ArrayList<Flight>> searchFlights(
            @RequestParam(required = false) String departureCity,
            @RequestParam(required = false) String arrivalCity,
            @RequestParam(required = false) LocalDate departureDate) {
        ArrayList<Flight> flights = flightService.findAllFlightBySearch(departureCity, arrivalCity, departureDate);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/search-roundtrip")
    public ResponseEntity<ArrayList<RoundTripFlightDTO>> searchRoundTripFlights(
            @RequestParam(required = true) String departureCity,
            @RequestParam(required = true) String arrivalCity,
            @RequestParam(required = true) LocalDate departureDate,
            @RequestParam(required = true) LocalDate returnDate) {

        ArrayList<RoundTripFlightDTO> roundTripFlights = flightService.searchRoundTripFlights(departureCity, arrivalCity, departureDate, returnDate);
        return ResponseEntity.ok(roundTripFlights);
    }
    @PutMapping("/update/{id}")
    public FlightDTO updateFlight(@PathVariable Long id,@RequestBody FlightDTO flightDTO){
        return flightService.updateFlight(id, flightDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFlight(@PathVariable Long id){
        return flightService.deleteFlight(id);
    }

    @GetMapping("/{id}")
    public Flight getFlightByid(@PathVariable Long id){
        return flightService.findFlightById(id);
    }
}
