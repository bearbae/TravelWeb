package com.bearcode.travelweb.services;

import com.bearcode.travelweb.models.TableRestaurant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface TableRestaurantService {

    ResponseEntity<String> addTableRestaurant(Long restaurantId, String tableRestaurantName, String tableRestaurantType,int area,
                                    BigDecimal price, String amenities,int quantity, MultipartFile multipartFile);
    ArrayList<TableRestaurant> getAllTableRestaurant(Long restaurantId);
    TableRestaurant updateTableRestaurant(Long idTableRestaurant , String tableRestaurantName, String tableRestaurantType, int area,
                      BigDecimal price, String amenities,int quantity, MultipartFile multipartFile);
    String deleteTableRestaurant(Long id);
    TableRestaurant findTableRestaurantById(Long id);
    String updateQuantity(Long id, int quantity);
}
