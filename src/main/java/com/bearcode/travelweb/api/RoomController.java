package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.HotelDTO;
import com.bearcode.travelweb.DTO.RoomDTO;
import com.bearcode.travelweb.models.Room;
import com.bearcode.travelweb.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import java.util.ArrayList;


@RestController
@RequestMapping("/api/admin/hotel/room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @PostMapping("/add/{hotelId}")
    public ResponseEntity<String> addRoom(@PathVariable Long hotelId,
                                          @RequestParam("roomName") String roomName,
                                          @RequestParam("roomType") String roomType,
                                          @RequestParam("view") String view,
                                          @RequestParam("area") int area,
                                          @RequestParam("pricePerNight")BigDecimal pricePerNight,
                                          @RequestParam("amenities") String amenities,
                                          @RequestParam("quantity") int quantity,
                                          @RequestParam("imageRoom")MultipartFile multipartFile){
        return roomService.addRoom(hotelId, roomName, roomType, view, area, pricePerNight, amenities,quantity, multipartFile);
    }

    @GetMapping("/all/{hotelId}")
    public ArrayList<Room> getAllRoom(@PathVariable Long hotelId){
        return roomService.getAllRoom(hotelId);
    }

    @PutMapping("/update/{id}")
    public Room updateRoom( @PathVariable Long id,
                               @RequestParam("roomName") String roomName,
                               @RequestParam("roomType") String roomType,
                               @RequestParam("view") String view,
                               @RequestParam("area") int area,
                               @RequestParam("pricePerNight")BigDecimal pricePerNight,
                               @RequestParam("amenities") String amenities,
                               @RequestParam("quantity") int quantity,
                               @RequestParam("imageRoom")MultipartFile multipartFile){
        return roomService.updateRoom(id, roomName, roomType, view, area, pricePerNight, amenities, quantity, multipartFile);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id){
        return roomService.deleteRoom(id);
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id){
        return roomService.findRoomById(id);
    }

    @PutMapping("/updateQuantity/{id}")
    public String updateQuantity(@PathVariable Long id,
                               @RequestParam("quantity") int quantity){
        return roomService.updateQuantitty(id, quantity);
    }
}
