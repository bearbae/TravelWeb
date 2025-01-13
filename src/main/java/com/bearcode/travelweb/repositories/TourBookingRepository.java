package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.DTO.TourBookingResponseDTO;
import com.bearcode.travelweb.models.TourBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourBookingRepository extends JpaRepository<TourBooking, Long> {

    @Query("SELECT DISTINCT new com.bearcode.travelweb.DTO.TourBookingResponseDTO(tb.id, tb.dateBook, tb.totalAmount, t.titleTour, t.id, tb.checkBrowser) " +
            "FROM TourBooking tb " +
            "JOIN tb.tour t " +
            "WHERE tb.user.id = :userId")
    List<TourBookingResponseDTO> findAllByUser_Id(@Param("userId") Long userId) ;

    @Query("SELECT DISTINCT new com.bearcode.travelweb.DTO.TourBookingResponseDTO(tb.id, tb.dateBook, tb.totalAmount, t.titleTour, t.id, tb.fullName, tb.email, tb.phoneNumber, tb.specialRequest, tb.checkBrowser) " +
            "FROM TourBooking tb " +
            "JOIN tb.tour t ")
    List<TourBookingResponseDTO> findAllBooks() ;
}
