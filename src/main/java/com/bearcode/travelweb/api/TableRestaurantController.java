package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.RestaurantDTO;
import com.bearcode.travelweb.DTO.TableRestaurantDTO;
import com.bearcode.travelweb.models.TableRestaurant;
import com.bearcode.travelweb.services.TableRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/admin/restaurant/table")
public class TableRestaurantController {

    @Autowired
    private TableRestaurantService tableRestaurantService;
    @PostMapping("/add/{restaurantId}")
    public ResponseEntity<String> addTableRestaurant(@PathVariable Long restaurantId,
                                          @RequestParam("tableRestaurantName") String tableRestaurantName,
                                          @RequestParam("tableRestaurantType") String tableRestaurantType,
                                          @RequestParam("area") int area,
                                          @RequestParam("price")BigDecimal price,
                                          @RequestParam("amenities") String amenities,
                                          @RequestParam("quantity") int quantity,
                                          @RequestParam("imageTable")MultipartFile multipartFile){
        return tableRestaurantService.addTableRestaurant(restaurantId, tableRestaurantName, tableRestaurantType, area, price, amenities,quantity, multipartFile);
    }

    @GetMapping("/all/{restaurantId}")
    public ArrayList<TableRestaurant> getAllTableRestaurant(@PathVariable Long restaurantId){
        return tableRestaurantService.getAllTableRestaurant(restaurantId);
    }

    @PutMapping("/update/{id}")
    public TableRestaurant updateTableRestaurant( @PathVariable Long id,
                            @RequestParam("tableRestaurantName") String tableRestaurantName,
                            @RequestParam("tableRestaurantType") String tableRestaurantType,
                            @RequestParam("area") int area,
                            @RequestParam("price")BigDecimal price,
                            @RequestParam("amenities") String amenities,
                            @RequestParam("quantity") int quantity,
                            @RequestParam("imageTable")MultipartFile multipartFile){
        return tableRestaurantService.updateTableRestaurant(id, tableRestaurantName, tableRestaurantType, area, price, amenities, quantity, multipartFile);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteTableRestaurant(@PathVariable Long id){
        return tableRestaurantService.deleteTableRestaurant(id);
    }

    @GetMapping("/{id}")
    public TableRestaurant getTableRestaurantById(@PathVariable Long id){
        return tableRestaurantService.findTableRestaurantById(id);
    }

    @PutMapping("/updateQuantity/{id}")
    public String updateQuantity(@PathVariable Long id,
                                 @RequestParam("quantity") int quantity){
        return tableRestaurantService.updateQuantity(id, quantity);
    }
}
