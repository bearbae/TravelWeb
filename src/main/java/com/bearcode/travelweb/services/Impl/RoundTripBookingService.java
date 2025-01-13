package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.DTO.RoundTripBookingRequestDTO;
import com.bearcode.travelweb.models.Flight;
import com.bearcode.travelweb.models.FlightBooking;
import com.bearcode.travelweb.models.User;
import com.bearcode.travelweb.repositories.FlightBookingRepository;
import com.bearcode.travelweb.repositories.FlightRepository;
import com.bearcode.travelweb.repositories.UserRepository;
import com.bearcode.travelweb.services.Impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class RoundTripBookingService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<String> createRoundTripBooking(RoundTripBookingRequestDTO bookingRequest) {
        try {
            // Lấy thông tin chuyến bay đi và về
            Flight departureFlight = flightRepository.findById(bookingRequest.getDepartureFlightId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay đi!"));
            Flight returnFlight = flightRepository.findById(bookingRequest.getReturnFlightId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay về!"));

            // Kiểm tra số ghế trống
            if (departureFlight.getAvailableSeats() < bookingRequest.getSeatCount() ||
                    returnFlight.getAvailableSeats() < bookingRequest.getSeatCount()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Không đủ số ghế trống cho chuyến bay đi hoặc về!");
            }

            // Lấy thông tin người dùng
            User user = userRepository.findById(bookingRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

            // Tính tổng giá vé
//            double totalAmount = (departureFlight.getPrice() + returnFlight.getPrice())
//                    * bookingRequest.getSeatCount();
            BigDecimal totalAmount = departureFlight.getPrice()
                    .add(returnFlight.getPrice())  // Cộng giá trị của giá vé đi và về
                    .multiply(BigDecimal.valueOf(bookingRequest.getSeatCount())); // Nhân với số lượng ghế


            // Tạo thông tin đặt vé cho chuyến đi
            FlightBooking departureBooking = new FlightBooking();
            departureBooking.setFullName(bookingRequest.getFullName());
            departureBooking.setPhoneNumber(bookingRequest.getPhoneNumber());
            departureBooking.setEmail(bookingRequest.getEmail());
            departureBooking.setDepartureDate(departureFlight.getDepartureDate());
//            departureBooking.setTotalAmount(departureFlight.getPrice() * bookingRequest.getSeatCount());
            departureBooking.setTotalAmount(departureFlight.getPrice()
                    .multiply(BigDecimal.valueOf(bookingRequest.getSeatCount())));
            departureBooking.setSeatCount(bookingRequest.getSeatCount());
            departureBooking.setFlight(departureFlight);
            departureBooking.setUser(user);

            // Tạo thông tin đặt vé cho chuyến về
            FlightBooking returnBooking = new FlightBooking();
            returnBooking.setFullName(bookingRequest.getFullName());
            returnBooking.setPhoneNumber(bookingRequest.getPhoneNumber());
            returnBooking.setEmail(bookingRequest.getEmail());
            returnBooking.setDepartureDate(returnFlight.getDepartureDate());
//            returnBooking.setTotalAmount(returnFlight.getPrice() * bookingRequest.getSeatCount());
            returnBooking.setTotalAmount(returnFlight.getPrice()
                    .multiply(BigDecimal.valueOf(bookingRequest.getSeatCount())));

            returnBooking.setSeatCount(bookingRequest.getSeatCount());
            returnBooking.setFlight(returnFlight);
            returnBooking.setUser(user);

            // Cập nhật số ghế trống
            departureFlight.setAvailableSeats(departureFlight.getAvailableSeats() - bookingRequest.getSeatCount());
            returnFlight.setAvailableSeats(returnFlight.getAvailableSeats() - bookingRequest.getSeatCount());
            flightRepository.save(departureFlight);
            flightRepository.save(returnFlight);

            // Lưu thông tin đặt vé vào cơ sở dữ liệu
            flightBookingRepository.save(departureBooking);
            flightBookingRepository.save(returnBooking);

            // Gửi email xác nhận
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
            String emailBody = "Cảm ơn bạn đã đặt vé khứ hồi!\n\n" +
                    "Thông tin chuyến đi:\n" +
                    "Chuyến bay: " + departureFlight.getFlightNumber() + "\n" +
                    "Điểm đi: " + departureFlight.getDepartureCity() + "\n" +
                    "Điểm đến: " + departureFlight.getArrivalCity() + "\n" +
                    "Ngày đi: " + departureFlight.getDepartureDate() + "\n" +
                    "Giá vé: " + numberFormat.format(departureFlight.getPrice()) + " VNĐ\n\n" +
                    "Thông tin chuyến về:\n" +
                    "Chuyến bay: " + returnFlight.getFlightNumber() + "\n" +
                    "Điểm đi: " + returnFlight.getDepartureCity() + "\n" +
                    "Điểm đến: " + returnFlight.getArrivalCity() + "\n" +
                    "Ngày về: " + returnFlight.getDepartureDate() + "\n" +
                    "Giá vé: " + numberFormat.format(returnFlight.getPrice()) + " VNĐ\n\n" +
                    "Số ghế đặt: " + bookingRequest.getSeatCount() + "\n" +

                    "Tổng giá vé: " + numberFormat.format(totalAmount) + " VNĐ\n" +
                    "Chúc bạn có chuyến bay an toàn!";


            emailService.sendBookingConfirmation(bookingRequest.getEmail(),
                    "Xác nhận đặt vé khứ hồi thành công", emailBody, "src/main/resources/static/assets/images/QR.jpg");

            return ResponseEntity.ok("Đặt vé khứ hồi thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }
}
