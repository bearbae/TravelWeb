package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.HotelDTO;
import com.bearcode.travelweb.models.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface HotelService {
//    String addHotel(HotelDTO hotelDTO);
    ResponseEntity<String> addnewHotel( String name,String location, BigDecimal price, int availableRooms,MultipartFile multipartFile, String description);
    // lấy danh sách khách sạn
    ArrayList<Hotel> getAllHotel();

//    ArrayList<Hotel> findAllHotelByLocation(String location);
    ArrayList<Hotel> findAllHotelByLocationAndPrice(String name, String location, String priceRange);

    Page<Hotel> findAllHotelByLocationAndPriceandNameForSearch(String name, String location, String priceRange, Pageable pageable);

    // sửa thông tin khách sạn
    Hotel updateHotel(Long id, String name,String location,BigDecimal price,
                      int availableRooms,MultipartFile multipartFile, String description);

    // xóa thông tin khách sạn
    String deleteHotel(Long id);

    // Lấy thông tin 1 khách sạn
    Hotel findHotelById(Long id);

//    Page<Hotel> getAllHotel(Pageable pageable);
}
