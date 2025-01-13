package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.Hotel;
import com.bearcode.travelweb.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Boolean existsByRoomNameAndAreaAndHotelId(String roomName, int area, Long hotelId);

    Optional<Room> getRoomById(Long id);

    Room findRoomById(Long id);

    ArrayList<Room> getAllByHotel_Id(Long hotelId);


}
