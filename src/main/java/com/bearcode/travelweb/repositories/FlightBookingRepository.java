package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.DTO.FlightBookingResponseDTO;
import com.bearcode.travelweb.DTO.HotelBookingResponseDTO;
import com.bearcode.travelweb.models.FlightBooking;
import com.bearcode.travelweb.models.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    List<FlightBooking> getAllByUser_Id(Long id);
    @Query("SELECT DISTINCT new com.bearcode.travelweb.DTO.FlightBookingResponseDTO(fb.id, f.departureDate, f.arrivalDate, fb.totalAmount, f.departureCity, f.arrivalCity, f.id, fb.checkBrowser) " +
            "FROM FlightBooking fb JOIN fb.flight f " +
            "WHERE fb.user.id = :userId")
    List<FlightBookingResponseDTO> findAllByUser_Id(@Param("userId") Long userId);


    @Query("SELECT DISTINCT new com.bearcode.travelweb.DTO.FlightBookingResponseDTO(fb.id, f.departureDate, f.arrivalDate, fb.totalAmount, f.departureCity, f.arrivalCity, f.id, fb.fullName, fb.email, fb.phoneNumber, fb.checkBrowser) " +
            "FROM FlightBooking fb " +
            "JOIN fb.flight f ")
    List<FlightBookingResponseDTO> findAllBooks() ;
}