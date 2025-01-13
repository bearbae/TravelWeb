package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.DTO.HotelBookingResponseDTO;
import com.bearcode.travelweb.models.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {

    List<HotelBooking> getAllByUser_Id(Long id);

    @Query("SELECT DISTINCT new com.bearcode.travelweb.DTO.HotelBookingResponseDTO(hb.id, hb.checkInDate, hb.totalAmount, h.name, h.id, hb.checkBrowser, r.roomName, hbr.quantity) " +
            "FROM HotelBooking hb " +
            "JOIN HotelBookingRoom hbr ON hb.id = hbr.hotelBooking.id " +
            "JOIN hbr.room r " +
            "JOIN r.hotel h " +
            "WHERE hb.user.id = :userId")
    List<HotelBookingResponseDTO> findAllByUser_Id(@Param("userId") Long userId) ;

    @Query("SELECT DISTINCT new com.bearcode.travelweb.DTO.HotelBookingResponseDTO(hb.id, hb.checkInDate, hb.totalAmount, h.name, h.id, hb.fullName, hb.email, hb.phoneNumber, hb.specialRequest, hb.checkBrowser, r.roomName, hbr.quantity) " +
            "FROM HotelBooking hb " +
            "JOIN HotelBookingRoom hbr ON hb.id = hbr.hotelBooking.id " +
            "JOIN hbr.room r " +
            "JOIN r.hotel h ")
    List<HotelBookingResponseDTO> findAllBooks() ;
}
