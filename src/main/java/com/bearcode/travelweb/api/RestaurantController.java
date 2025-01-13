package com.bearcode.travelweb.api;


import com.bearcode.travelweb.models.Restaurant;
import com.bearcode.travelweb.repositories.RestaurantRepository;
import com.bearcode.travelweb.services.RestaurantService;
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
@RequestMapping("/api/admin/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/all")
    public ArrayList<Restaurant> getAllRestaurant(){
        return restaurantService.getAllRestaurant();
    }

    @GetMapping("/allForUser")
    public Page<Restaurant> getAllRestaurant(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "9") int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return restaurantRepository.findAll(pageable);
    }


    @GetMapping("/searchForUser")
    public Page<Restaurant> findAllRestaurantByLocation(@RequestParam("nameRestaurant") String name,
                                              @RequestParam("location") String location,
                                              @RequestParam("priceRestaurant") String priceRange,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "9") int limit){
        Pageable pageable = PageRequest.of(page, limit);
        return restaurantService.findAllRestaurantByLocationAndPriceandNameForSearch(name, location, priceRange, pageable);

    }

    @GetMapping("/search")
    public ArrayList<Restaurant> findAllRestaurantByLocation(@RequestParam("nameRestaurant") String name,
                                                   @RequestParam("location") String location,
                                                   @RequestParam("priceRestaurant") String priceRange){
        return restaurantService.findAllRestaurantByLocationAndPrice(name, location, priceRange);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRestaurant(@RequestParam("name") String name,
                                                @RequestParam("location") String location,
                                                @RequestParam("price") BigDecimal price,
                                                @RequestParam("availableTables") int availableTables,
                                                @RequestParam("imageRestaurant") MultipartFile multipartFile,
                                                @RequestParam("description") String description){
        return restaurantService.addnewRestaurant(name, location, price, availableTables, multipartFile, description);
    }

    @PutMapping("/update/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id,
                             @RequestParam("name") String name,
                             @RequestParam("location") String location,
                             @RequestParam("price") BigDecimal price,
                             @RequestParam("availableTables") int availableTables,
                             @RequestParam("imageRestaurant") MultipartFile multipartFile,
                             @RequestParam("description") String description){
        return restaurantService.updateRestaurant(id, name, location, price, availableTables, multipartFile, description);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id){
        return restaurantService.deleteRestaurant(id);
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id){
        return restaurantService.findRestaurantById(id);
    }

    @GetMapping("/getByLocation/{location}")
    public List<Restaurant> getRestaurantsByLocation(@PathVariable String location){
        return restaurantRepository.getRestaurantsByLocation(location);
    }
}
