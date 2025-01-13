package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.TourBookingDTO;
import com.bearcode.travelweb.DTO.TourBookingResponseDTO;
import com.bearcode.travelweb.models.TourBooking;
import com.bearcode.travelweb.repositories.TourBookingRepository;
import com.bearcode.travelweb.services.Impl.EmailService;
import com.bearcode.travelweb.services.TourBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@RequestMapping("/api/user/tour/booking/")
@RestController
public class TourBookingController {
    @Autowired
    private TourBookingService tourBookingService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TourBookingRepository tourBookingRepository;

    @PostMapping("/add")
    public ResponseEntity<TourBooking> createBooking(@RequestBody TourBookingDTO tourBookingDTO){
        try {
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        // Gửi email xác nhận sau khi đặt tour thành công
        String emailBody = "Cảm ơn bạn đã dịch vụ tour du lịch! Dưới đây là thông tin chi tiết đặt tour của bạn:\n" +
                "Họ và tên: " + tourBookingDTO.getFullName() + "\n" +
                "Ngày check-in: " + tourBookingDTO.getDateBook() + "\n" +
                "Yêu cầu của bạn: " + tourBookingDTO.getSpecialRequest() + "\n" +
                "Loại tour: " + tourBookingDTO.getTourDuration() + "-" +tourBookingDTO.getTypeService() + "\n" +
                "Hướng dẫn viên: " + tourBookingDTO.getHdv() + "\n" +
                "Tổng số tiền: " + numberFormat.format(tourBookingDTO.getTotalAmount()) + " VNĐ\n\n" +
                "Chúc bạn có kỳ nghỉ tuyệt vời!";

        String qrImagePath = "src/main/resources/static/assets/images/QR.jpg";
        emailService.sendBookingConfirmation(tourBookingDTO.getEmail(),
                "Xác nhận đặt tour thành công", emailBody, qrImagePath);
        return tourBookingService.createNewBooking(tourBookingDTO);
    } catch (Exception e) {
        throw new Error("Looxi" + e.getMessage());
    }
    }

    @GetMapping("/userId/{userId}")
    public List<TourBookingResponseDTO> getBookings(@PathVariable Long userId){
        return tourBookingRepository.findAllByUser_Id(userId);
    }
    @GetMapping("/all")
    public List<TourBookingResponseDTO> getAllBooks(){
        return tourBookingRepository.findAllBooks();
    }
    @DeleteMapping("/delete/{id}")
    public String deleteBookTour(@PathVariable Long id){
        return tourBookingService.deleteBooking(id);
    }
    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveBooking(@PathVariable Long id) {
        return tourBookingService.setCheck(id);
    }
}
