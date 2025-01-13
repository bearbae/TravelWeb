package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.BookingTableRestaurantDTO;
import com.bearcode.travelweb.DTO.HotelBookingResponseDTO;
import com.bearcode.travelweb.DTO.RestaurantBookingResponseDTO;
import com.bearcode.travelweb.models.RestaurantBooking;
import com.bearcode.travelweb.models.TableRestaurant;
import com.bearcode.travelweb.repositories.HotelBookingRepository;
import com.bearcode.travelweb.repositories.RestaurantBookingRepository;
import com.bearcode.travelweb.services.RestaurantBookingService;
import com.bearcode.travelweb.services.Impl.EmailService;
import com.bearcode.travelweb.services.TableRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/user/booking")
public class RestaurantBookingController {
    @Autowired
    private RestaurantBookingService restaurantBookingService;
    @Autowired
    private TableRestaurantService tableRestaurantService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RestaurantBookingRepository restaurantBookingRepository;

    @PostMapping("/new")
    public ResponseEntity<String> createBookingTableRestaurant(@RequestBody BookingTableRestaurantDTO bookingTableRestaurant) {
        try {
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

            StringBuilder tableRestaurantDetails = new StringBuilder();
            bookingTableRestaurant.getTableRestaurants().forEach((tableRestaurantId, quantity) -> {
                TableRestaurant tableRestaurant = tableRestaurantService.findTableRestaurantById(tableRestaurantId);
                tableRestaurantDetails.append("- Tên Bàn: ").append(tableRestaurant.getTableRestaurantName()).append("\n")
                        .append("  + Tầng: ").append(tableRestaurant.getTableRestaurantType()).append("\n")
                        .append("  + Sức chứa: ").append(tableRestaurant.getArea()).append(" m2\n")
                        .append("  + Giá: ").append( numberFormat.format(tableRestaurant.getPrice())).append(" VNĐ\n")
                        .append("  + Số lượng: ").append(quantity).append("\n")
                        .append("\n");
            });

            String emailBody = "Cảm ơn bạn đã đặt bàn! Dưới đây là thông tin chi tiết đặt bàn của bạn:\n" +
                    "Họ và tên: " + bookingTableRestaurant.getFullName() + "\n" +
                    "Ngày check-in: " + bookingTableRestaurant.getCheckInDate() + "\n" +
                    "Yêu cầu của bạn: " + bookingTableRestaurant.getSpecialTable() + "\n" +
                    "Tổng số tiền: " + numberFormat.format(bookingTableRestaurant.getTotalAmount()) + " VNĐ\n\n" +
                    "Thông tin bàn:\n" + tableRestaurantDetails +
                    "Chúc bạn dùng bữa ngon miệng!";

            String qrImagePath = "src/main/resources/static/assets/images/QR.jpg";
            emailService.sendBookingConfirmation(bookingTableRestaurant.getEmail(),
                    "Xác nhận đặt bàn thành công", emailBody, qrImagePath);

            return restaurantBookingService.createBookingTableRestaurant(bookingTableRestaurant);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }

    @GetMapping("/userId/{userId}")
    public List<RestaurantBookingResponseDTO> getBookings(@PathVariable Long userId){
        return restaurantBookingRepository.findAllByUser_Id(userId);
    }

    @GetMapping("/all")
    public List<RestaurantBookingResponseDTO> getBooksForAdmin(){
        return restaurantBookingRepository.findAllBooks();
    }
    @DeleteMapping("/delete/{id}")
    public String deleteBookRestaurant(@PathVariable Long id){
        return restaurantBookingService.deleteBooking(id);
    }


    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveBooking(@PathVariable Long id) {
        return restaurantBookingService.setCheck(id);
    }

}
