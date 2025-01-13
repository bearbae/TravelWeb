package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.DTO.HotelBookingResponseDTO;
import com.bearcode.travelweb.DTO.RestaurantBookingResponseDTO;
import com.bearcode.travelweb.models.HotelBooking;
import com.bearcode.travelweb.models.RestaurantBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantBookingRepository extends JpaRepository<RestaurantBooking, Long> {

    List<RestaurantBooking> getAllByUser_Id(Long userId);

    @Query("SELECT DISTINCT new com.bearcode.travelweb.DTO.RestaurantBookingResponseDTO(rb.id, rb.checkInDate, rb.totalAmount, r.name, r.id, rb.checkBrowser, t.tableRestaurantName, rbt.quantity) " +
            "FROM RestaurantBooking rb " +
            "JOIN RestaurantBookingTable rbt ON rb.id = rbt.restaurantBooking.id " +
            "JOIN rbt.tableRestaurant t " +
            "JOIN t.restaurant r " +
            "WHERE rb.user.id = :userId")
    List<RestaurantBookingResponseDTO> findAllByUser_Id(@Param("userId") Long userId) ;

    @Query("SELECT DISTINCT new com.bearcode.travelweb.DTO.RestaurantBookingResponseDTO(rb.id, rb.checkInDate, rb.totalAmount, r.name, r.id, rb.fullName, rb.email, rb.phoneNumber, rb.specialTable, rb.checkBrowser, t.tableRestaurantName, rbt.quantity) " +
            "FROM RestaurantBooking rb " +
            "JOIN RestaurantBookingTable rbt ON rb.id = rbt.restaurantBooking.id " +
            "JOIN rbt.tableRestaurant t " +
            "JOIN t.restaurant r ")
    List<RestaurantBookingResponseDTO> findAllBooks() ;
}
