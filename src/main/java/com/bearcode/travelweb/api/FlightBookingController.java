package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.FlightBookingRequestDTO;
import com.bearcode.travelweb.DTO.FlightBookingResponseDTO;
import com.bearcode.travelweb.DTO.RoundTripBookingRequestDTO;
import com.bearcode.travelweb.repositories.FlightBookingRepository;
import com.bearcode.travelweb.services.FlightBookingService;
import com.bearcode.travelweb.services.Impl.RoundTripBookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/bookings-flight")
public class FlightBookingController {
    @Autowired
    private FlightBookingService flightBookingService;

    @Autowired
    private RoundTripBookingService roundTripBookingService;
    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @PostMapping("/flight/new")
    public ResponseEntity<String> createFlightBooking(@RequestBody FlightBookingRequestDTO bookingRequest) {
        return flightBookingService.createBooking(bookingRequest);
    }

    @PostMapping("/flight/roundtrip")
    public ResponseEntity<String> createRoundTripBooking(@RequestBody RoundTripBookingRequestDTO bookingRequest) {
        return roundTripBookingService.createRoundTripBooking(bookingRequest);
    }


    @GetMapping("/userId/{userId}")
    public List<FlightBookingResponseDTO> getBookings(@PathVariable Long userId){
        return flightBookingRepository.findAllByUser_Id(userId);
    }

    @GetMapping("/all")
    public List<FlightBookingResponseDTO> getBooksForAdmin(){
        return flightBookingRepository.findAllBooks();
    }
//    @DeleteMapping("/delete/{id}")
//    public String deleteBookFlight(@PathVariable Long id){
//        return flightBookingService.deleteBooking(id);
//    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        try {
            String message = flightBookingService.deleteBooking(id);
            return ResponseEntity.ok(message);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống. Vui lòng thử lại sau!");
        }
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveBooking(@PathVariable Long id) {
        return flightBookingService.setCheck(id);
    }

}