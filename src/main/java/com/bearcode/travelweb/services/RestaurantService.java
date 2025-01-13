package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.RestaurantDTO;
import com.bearcode.travelweb.models.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface RestaurantService {
    ResponseEntity<String> addnewRestaurant( String name,String location, BigDecimal price, int availableTables,MultipartFile multipartFile, String description);
    ArrayList<Restaurant> getAllRestaurant();
    ArrayList<Restaurant> findAllRestaurantByLocationAndPrice(String name, String location, String priceRange);
    Page<Restaurant> findAllRestaurantByLocationAndPriceandNameForSearch(String name, String location, String priceRange, Pageable pageable);
    Restaurant updateRestaurant(Long id, String name,String location,BigDecimal price,
                      int availableTables,MultipartFile multipartFile, String description);
    String deleteRestaurant(Long id);
    Restaurant findRestaurantById(Long id);
}
