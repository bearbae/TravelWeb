package com.bearcode.travelweb.services;

import com.bearcode.travelweb.models.Room;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface RoomService {
//    String addRoom(Long hotelId,RoomDTO roomDTO);

    ResponseEntity<String> addRoom(Long hotelId, String roomName, String roomType, String view, int area,
                                   BigDecimal pricePerNight, String amenities,int quantity, MultipartFile multipartFile);
    // lấy danh sách phòng
    ArrayList<Room> getAllRoom(Long hotelId);

    // sửa thông tin phòng
//    RoomDTO updateRoom(Long id, RoomDTO roomDTO);

    Room updateRoom(Long idRoom , String roomName, String roomType, String view, int area,
                    BigDecimal pricePerNight, String amenities,int quantity, MultipartFile multipartFile);

    // xóa thông tin phòng
    String deleteRoom(Long id);

    // Lấy thông tin 1 phòng
    Room findRoomById(Long id);

    String updateQuantitty(Long id, int quantity);
}
