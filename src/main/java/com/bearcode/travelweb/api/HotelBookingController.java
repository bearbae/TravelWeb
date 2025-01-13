package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.BookingRequestDTO;
import com.bearcode.travelweb.DTO.HotelBookingResponseDTO;
import com.bearcode.travelweb.models.HotelBooking;
import com.bearcode.travelweb.models.Room;
import com.bearcode.travelweb.repositories.HotelBookingRepository;
import com.bearcode.travelweb.services.HotelBookingService;
import com.bearcode.travelweb.services.Impl.EmailService;
import com.bearcode.travelweb.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/user/bookings")
public class HotelBookingController {
    @Autowired
    private HotelBookingService hotelBookingService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    @PostMapping("/new")
    public ResponseEntity<String> createBooking(@RequestBody BookingRequestDTO bookingRequest) {
        try {
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

            StringBuilder roomDetails = new StringBuilder();
            bookingRequest.getRooms().forEach((roomId, quantity) -> {
                Room room = roomService.findRoomById(roomId);
                roomDetails.append("- Tên Phòng: ").append(room.getRoomName()).append("\n")
                        .append("  + Loại phòng: ").append(room.getRoomType()).append("\n")
                        .append("  + Cảnh: ").append(room.getView()).append("\n")
                        .append("  + Diện tích: ").append(room.getArea()).append(" m2\n")
                        .append("  + Giá: ").append( numberFormat.format(room.getPricePerNight())).append(" VNĐ\n")
                        .append("  + Số lượng: ").append(quantity).append("\n")
                        .append("\n");
            });

            // Gửi email xác nhận sau khi đặt phòng thành công
            String emailBody = "Cảm ơn bạn đã đặt phòng! Dưới đây là thông tin chi tiết đặt phòng của bạn:\n" +
                    "Họ và tên: " + bookingRequest.getFullName() + "\n" +
                    "Ngày check-in: " + bookingRequest.getCheckInDate() + "\n" +
                    "Yêu cầu của bạn: " + bookingRequest.getSpecialRequest() + "\n" +
                    "Tổng số tiền: " + numberFormat.format(bookingRequest.getTotalAmount()) + " VNĐ\n\n" +
                    "Thông tin phòng:\n" + roomDetails +
                    "Chúc bạn có kỳ nghỉ tuyệt vời!";

            String qrImagePath = "src/main/resources/static/assets/images/QR.jpg";
            emailService.sendBookingConfirmation(bookingRequest.getEmail(),
                    "Xác nhận đặt phòng thành công", emailBody, qrImagePath);

            return hotelBookingService.createBooking(bookingRequest);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }

    @GetMapping("/userId/{userId}")
    public List<HotelBookingResponseDTO> getBookings(@PathVariable Long userId){
        return hotelBookingRepository.findAllByUser_Id(userId);
    }

    @GetMapping("/all")
    public List<HotelBookingResponseDTO> getBooksForAdmin(){
        return hotelBookingRepository.findAllBooks();
    }
    @DeleteMapping("/delete/{id}")
    public String deleteBookHotel(@PathVariable Long id){
        return hotelBookingService.deleteBooking(id);
    }


    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveBooking(@PathVariable Long id) {
        return hotelBookingService.setCheck(id);
    }
}
