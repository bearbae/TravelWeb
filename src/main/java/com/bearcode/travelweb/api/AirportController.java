package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.AirportDTO;
import com.bearcode.travelweb.models.Airport;
import com.bearcode.travelweb.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/airport")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @PostMapping("/add")
    public ResponseEntity<String> addAirport(@RequestBody AirportDTO airportDTO){
        String response = airportService.addAirport(airportDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Airport>> getAllAirports(){
        List<Airport> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airports);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AirportDTO> updateAirport(@PathVariable Long id, @RequestBody AirportDTO airportDTO){
        AirportDTO updatedAirport = airportService.updateAirport(id, airportDTO);
        return ResponseEntity.ok(updatedAirport);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAirport(@PathVariable Long id){
        String response = airportService.deleteAirport(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id){
        Airport airport = airportService.findAirportById(id);
        return ResponseEntity.ok(airport);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam String name) {
        List<Airport> airports = airportService.searchAirportsByName(name);
        return ResponseEntity.ok(airports);
    }
}
