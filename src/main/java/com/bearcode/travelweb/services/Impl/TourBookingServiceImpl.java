package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.DTO.TourBookingDTO;
import com.bearcode.travelweb.models.HotelBooking;
import com.bearcode.travelweb.models.TourBooking;
import com.bearcode.travelweb.repositories.TourBookingRepository;
import com.bearcode.travelweb.services.TourBookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TourBookingServiceImpl implements TourBookingService {
    @Autowired
    private TourBookingRepository tourBookingRepository;
    @Override
    public ResponseEntity<TourBooking> createNewBooking(TourBookingDTO tourBookingDTO) {
        TourBooking tourBooking = new TourBooking();
        tourBooking.setDateBook(tourBookingDTO.getDateBook());
        tourBooking.setPhoneNumber(tourBookingDTO.getPhoneNumber());
        tourBooking.setTourDuration(tourBookingDTO.getTourDuration());
        tourBooking.setTypeService(tourBookingDTO.getTypeService());
        tourBooking.setHdv(tourBookingDTO.getHdv());
        tourBooking.setQuantityAdults(tourBookingDTO.getQuantityAdults());
        tourBooking.setQuantityChildren(tourBookingDTO.getQuantityChildren());
        tourBooking.setTotalAmount(tourBookingDTO.getTotalAmount());
        tourBooking.setFullName(tourBookingDTO.getFullName());
        tourBooking.setEmail(tourBookingDTO.getEmail());
        tourBooking.setSpecialRequest(tourBookingDTO.getSpecialRequest());
        tourBooking.setUser(tourBookingDTO.getUser());
        tourBooking.setTour(tourBookingDTO.getTour());
        tourBookingRepository.save(tourBooking);
        return ResponseEntity.ok(tourBooking);
    }

    @Override
    public String deleteBooking(Long id) {
        Optional<TourBooking> tourBookingOptional = tourBookingRepository.findById(id);
        if(tourBookingOptional.isPresent()){
            tourBookingRepository.deleteById(id);
            return "Xóa Thành công!";
        }
        throw new EntityNotFoundException("Không tồn tại bookTour với id: "+ id);
    }

    @Override
    public ResponseEntity<String> setCheck(Long id) {
        try {
            TourBooking booking = tourBookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với id: " + id));
            booking.setCheckBrowser(1);
            tourBookingRepository.save(booking);
            return ResponseEntity.ok("Duyệt thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duyệt thất bại: " + e.getMessage());
        }
    }
}
