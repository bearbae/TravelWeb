package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.DTO.BookingTableRestaurantDTO;
import com.bearcode.travelweb.models.*;
import com.bearcode.travelweb.repositories.RestaurantBookingRepository;
import com.bearcode.travelweb.repositories.RestaurantBookingTableRepository;
import com.bearcode.travelweb.repositories.TableRestaurantRepository;
import com.bearcode.travelweb.services.RestaurantBookingService;
import com.bearcode.travelweb.services.TableRestaurantService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RestaurantBookingServiceImpl implements RestaurantBookingService {

    @Autowired
    private RestaurantBookingRepository restaurantBookingRepository;
    @Autowired
    private TableRestaurantService tableRestaurantService ;
    @Autowired
    private TableRestaurantRepository tableRestaurantRepository ;
    @Autowired
    private RestaurantBookingTableRepository restaurantBookingTableRepository;
    @Override
    public ResponseEntity<String> createBookingTableRestaurant(BookingTableRestaurantDTO bookingTableRestaurant) {
        RestaurantBooking restaurantBooking = new RestaurantBooking();
        restaurantBooking.setCheckInDate(bookingTableRestaurant.getCheckInDate());
        restaurantBooking.setFullName(bookingTableRestaurant.getFullName());
        restaurantBooking.setPhoneNumber(bookingTableRestaurant.getPhoneNumber());
        restaurantBooking.setEmail(bookingTableRestaurant.getEmail());
        restaurantBooking.setSpecialTable(bookingTableRestaurant.getSpecialTable());
        restaurantBooking.setTotalAmount(bookingTableRestaurant.getTotalAmount());
        restaurantBooking.setUser(bookingTableRestaurant.getUser());
        restaurantBookingRepository.save(restaurantBooking);

        bookingTableRestaurant.getTableRestaurants().forEach((tableRestaurantId, quantity) -> {
            RestaurantBookingTable restaurantBookingTable = new RestaurantBookingTable();
            restaurantBookingTable.setRestaurantBooking(restaurantBooking);
            restaurantBookingTable.setTableRestaurant(tableRestaurantService.findTableRestaurantById(tableRestaurantId));
            restaurantBookingTable.setQuantity(quantity);
            restaurantBookingTableRepository.save(restaurantBookingTable);
        });
        return ResponseEntity.ok("Đặt bàn thành công!");
    }
//    @Override
//    public String deleteBooking(Long id) {
//        Optional<RestaurantBooking> restaurantOptional = restaurantBookingRepository.findById(id);
//        if(restaurantOptional.isPresent()){
//            restaurantBookingRepository.deleteById(id);
//            return "Xóa Thành công!";
//        }
//        throw new EntityNotFoundException("Không tồn tại bookRestaurant với id: "+ id);
//    }

    @Override
    public String deleteBooking(Long id) {
        Optional<RestaurantBooking> restaurantBookingOptional = restaurantBookingRepository.findById(id);
        if(restaurantBookingOptional.isPresent()){
            RestaurantBooking restaurantBooking = restaurantBookingOptional.get();

            for (RestaurantBookingTable bookingTableRestaurant : restaurantBooking.getRestaurantBookingTables()) {
                TableRestaurant tableRestaurant = bookingTableRestaurant.getTableRestaurant();
                tableRestaurant.setQuantity(tableRestaurant.getQuantity() + bookingTableRestaurant.getQuantity());
                tableRestaurantRepository.save(tableRestaurant);
            }

            restaurantBookingRepository.deleteById(id);
            return "Xóa Thành công!";
        }
        throw new EntityNotFoundException("Không tồn tại booking với id: " + id);
    }

    @Override
    public ResponseEntity<String> setCheck(Long id) {
        try {
            RestaurantBooking booking = restaurantBookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với id: " + id));
            booking.setCheckBrowser(1);
            restaurantBookingRepository.save(booking);
            return ResponseEntity.ok("Duyệt thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duyệt thất bại: " + e.getMessage());
        }
    }
}
