package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.DTO.BookingRequestDTO;
import com.bearcode.travelweb.models.*;
import com.bearcode.travelweb.repositories.HotelBookingRepository;
import com.bearcode.travelweb.repositories.HotelBookingRoomRepository;
import com.bearcode.travelweb.repositories.RoomRepository;
import com.bearcode.travelweb.services.HotelBookingService;
import com.bearcode.travelweb.services.RoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class HotelBookingServiceImpl implements HotelBookingService {

    @Autowired
    private HotelBookingRepository hotelBookingRepository;
    @Autowired
    private RoomService roomService ;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelBookingRoomRepository hotelBookingRoomRepository;
    @Override
    public ResponseEntity<String> createBooking(BookingRequestDTO bookingRequest) {
        HotelBooking hotelBooking = new HotelBooking();
        hotelBooking.setCheckInDate(bookingRequest.getCheckInDate());
        hotelBooking.setFullName(bookingRequest.getFullName());
        hotelBooking.setPhoneNumber(bookingRequest.getPhoneNumber());
        hotelBooking.setEmail(bookingRequest.getEmail());
        hotelBooking.setSpecialRequest(bookingRequest.getSpecialRequest());
        hotelBooking.setTotalAmount(bookingRequest.getTotalAmount());
        hotelBooking.setUser(bookingRequest.getUser());
        hotelBookingRepository.save(hotelBooking);

        // Lưu thông tin  hotel_booking_rooms
        bookingRequest.getRooms().forEach((roomId, quantity) -> {
            HotelBookingRoom hotelBookingRoom = new HotelBookingRoom();
            hotelBookingRoom.setHotelBooking(hotelBooking);
            hotelBookingRoom.setRoom(roomService.findRoomById(roomId));
            hotelBookingRoom.setQuantity(quantity);
            hotelBookingRoomRepository.save(hotelBookingRoom);
        });
        return ResponseEntity.ok("Đặt phòng thành công!");
    }

//    @Override
//    public String deleteBooking(Long id) {
//        Optional<HotelBooking> hotelOptional = hotelBookingRepository.findById(id);
//        if(hotelOptional.isPresent()){
//            hotelBookingRepository.deleteById(id);
//            return "Xóa Thành công!";
//        }
//        throw new EntityNotFoundException("Không tồn tại bookHotel với id: "+ id);
//    }
@Override
public String deleteBooking(Long id) {
    Optional<HotelBooking> hotelBookingOptional = hotelBookingRepository.findById(id);
    if(hotelBookingOptional.isPresent()){
        HotelBooking hotelBooking = hotelBookingOptional.get();

        for (HotelBookingRoom bookingRoom : hotelBooking.getHotelBookingRooms()) {
            Room room = bookingRoom.getRoom();
            room.setQuantity(room.getQuantity() + bookingRoom.getQuantity());
            roomRepository.save(room);
        }

        hotelBookingRepository.deleteById(id);
        return "Xóa Thành công!";
    }
    throw new EntityNotFoundException("Không tồn tại booking với id: " + id);
}



    @Override
    public ResponseEntity<String> setCheck(Long id) {
        try {
            HotelBooking booking = hotelBookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với id: " + id));
            booking.setCheckBrowser(1);
            hotelBookingRepository.save(booking);
            return ResponseEntity.ok("Duyệt thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duyệt thất bại: " + e.getMessage());
        }
    }
}
