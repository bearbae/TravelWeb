package com.bearcode.travelweb.api;


import com.bearcode.travelweb.models.Hotel;
import com.bearcode.travelweb.repositories.HotelRepository;
import com.bearcode.travelweb.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/api/admin/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/all")
    public ArrayList<Hotel> getAllHotel(){
        return hotelService.getAllHotel();
    }

    @GetMapping("/allForUser")
    public Page<Hotel> getAllHotel(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "9") int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return hotelRepository.findAll(pageable);
    }


    @GetMapping("/searchForUser")
    public Page<Hotel> findAllHotelByLocation(@RequestParam("nameHotel") String name,
                                                   @RequestParam("location") String location,
                                                   @RequestParam("priceHotel") String priceRange,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "9") int limit){
        Pageable pageable = PageRequest.of(page, limit);
        return hotelService.findAllHotelByLocationAndPriceandNameForSearch(name, location, priceRange, pageable);

    }

    @GetMapping("/search")
    public ArrayList<Hotel> findAllHotelByLocation(@RequestParam("nameHotel") String name,
                                                   @RequestParam("location") String location,
                                                   @RequestParam("priceHotel") String priceRange){
        return hotelService.findAllHotelByLocationAndPrice(name,location, priceRange);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addHotel(@RequestParam("name") String name,
                                           @RequestParam("location") String location,
                                           @RequestParam("price") BigDecimal price,
                                           @RequestParam("availableRooms") int availableRooms,
                                           @RequestParam("imageHotel") MultipartFile multipartFile,
                                           @RequestParam("description") String description){
        return hotelService.addnewHotel(name, location, price, availableRooms, multipartFile, description);
    }

    @PutMapping("/update/{id}")
    public Hotel updateHotel(@PathVariable Long id,
                                @RequestParam("name") String name,
                                @RequestParam("location") String location,
                                @RequestParam("price") BigDecimal price,
                                @RequestParam("availableRooms") int availableRooms,
                                @RequestParam("imageHotel") MultipartFile multipartFile,
                             @RequestParam("description") String description){
        return hotelService.updateHotel(id, name, location, price, availableRooms, multipartFile, description);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id){
        return hotelService.deleteHotel(id);
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Long id){
        return hotelService.findHotelById(id);
    }

//    @GetMapping("/all")
//    public ResponseEntity<Page<Hotel>> getAllHotels(Pageable pageable) {
//        Page<Hotel> hotels = hotelService.getAllHotel(pageable);
//        return ResponseEntity.ok(hotels);
//    }

    @GetMapping("/getByLocation/{address}")
    public List<Hotel> getHotelsByLocation(@PathVariable String address){
        return hotelRepository.getHotelsByAddress(address);
    }
 }
