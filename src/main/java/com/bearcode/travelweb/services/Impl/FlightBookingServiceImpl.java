package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.DTO.FlightBookingRequestDTO;
import com.bearcode.travelweb.models.Flight;
import com.bearcode.travelweb.models.FlightBooking;
import com.bearcode.travelweb.models.HotelBooking;
import com.bearcode.travelweb.models.User;
import com.bearcode.travelweb.repositories.FlightBookingRepository;
import com.bearcode.travelweb.repositories.FlightRepository;
import com.bearcode.travelweb.repositories.UserRepository;
import com.bearcode.travelweb.services.FlightBookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

@Service
public class FlightBookingServiceImpl implements FlightBookingService {
    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<String> createBooking(FlightBookingRequestDTO bookingRequest) {
        try {
            // Lấy thông tin chuyến bay
            Flight flight = flightRepository.findById(bookingRequest.getFlightId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay!"));

            if (flight.getAvailableSeats() < bookingRequest.getSeatCount()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Không đủ số ghế trống cho chuyến bay này!");
            }

            // Lấy thông tin người dùng
            User user = userRepository.findById(bookingRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

            // Tạo thông tin đặt vé
            FlightBooking booking = new FlightBooking();
            booking.setFullName(bookingRequest.getFullName());
            booking.setPhoneNumber(bookingRequest.getPhoneNumber());
            booking.setEmail(bookingRequest.getEmail());
            booking.setDepartureDate(flight.getDepartureDate());
//            booking.setTotalAmount(flight.getPrice().doubleValue() * bookingRequest.getSeatCount());
            booking.setTotalAmount(flight.getPrice().multiply(BigDecimal.valueOf(bookingRequest.getSeatCount())));
            booking.setSeatCount(bookingRequest.getSeatCount());
            booking.setFlight(flight);
            booking.setUser(user);

            // Giảm số ghế trống
            flight.setAvailableSeats(flight.getAvailableSeats() - bookingRequest.getSeatCount());
            flightRepository.save(flight);

            flightBookingRepository.save(booking);

            // Gửi email xác nhận
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
            String emailBody = "Cảm ơn bạn đã đặt vé!\n\n" +
                    "Chuyến bay: " + flight.getFlightNumber() + "\n" +
                    "Điểm đi: " + flight.getDepartureCity() + "\n" +
                    "Điểm đến: " + flight.getArrivalCity() + "\n" +
                    "Ngày đi: " + flight.getDepartureDate() + "\n" +
                    "Số ghế đặt: " + bookingRequest.getSeatCount() + "\n" +
                    "Tổng giá vé: " + numberFormat.format(booking.getTotalAmount()) + " VNĐ\n" +
                    "Chúc bạn có chuyến bay an toàn!";

            emailService.sendBookingConfirmation(bookingRequest.getEmail(),
                    "Xác nhận đặt vé thành công", emailBody, "src/main/resources/static/assets/images/QR.jpg");

            return ResponseEntity.ok("Đặt vé thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public String deleteBooking(Long id) {
        Optional<FlightBooking> bookingOptional = flightBookingRepository.findById(id);

        if (bookingOptional.isPresent()) {
            FlightBooking booking = bookingOptional.get();

            Flight flight = booking.getFlight();

            if (flight != null) {
                flight.setAvailableSeats(flight.getAvailableSeats() + booking.getSeatCount());
                flightRepository.save(flight);
            }

            flightBookingRepository.deleteById(id);
            return "Xóa Thành công!";
        }

        throw new EntityNotFoundException("Không tồn tại bookFlight với id: " + id);
    }

    @Override
    public ResponseEntity<String> setCheck(Long id) {
        try {
            FlightBooking booking = flightBookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với id: " + id));
            booking.setCheckBrowser(1);
            flightBookingRepository.save(booking);
            return ResponseEntity.ok("Duyệt thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duyệt thất bại: " + e.getMessage());
        }
    }

}