package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.HotelBookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelBookingRoomRepository extends JpaRepository<HotelBookingRoom, Long> {
}
