package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.api.RestaurantSpecification;
import com.bearcode.travelweb.mappers.RestaurantMapper;
import com.bearcode.travelweb.models.Restaurant;
import com.bearcode.travelweb.repositories.RestaurantRepository;
import com.bearcode.travelweb.services.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final String UPLOAD_DIR = "src/main/resources/static/uploadsRestaurant/";

    @Autowired
    private RestaurantMapper restaurantMapper;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public ResponseEntity<String> addnewRestaurant(String name,String location,BigDecimal price,
                                              int availableTables,MultipartFile multipartFile, String description){
        if(multipartFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Khoong duoc de anh trong");
        }
        if(restaurantRepository.existsByLocation(location) && restaurantRepository.existsByName(name)){
            return ResponseEntity.status(HttpStatus.OK).body("Nhà hàng đã tồn tại!");
        } else {
            try{
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                Path uploadPath = Paths.get(UPLOAD_DIR + fileName);

                if (!Files.exists(uploadPath.getParent())) {
                    Files.createDirectories(uploadPath.getParent());
                }

                Files.copy(multipartFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

                Restaurant restaurant = new Restaurant();
                restaurant.setName(name);
                restaurant.setLocation(location);
                restaurant.setPrice(price);
                restaurant.setAvailableTables(availableTables);
                restaurant.setImageRestaurant(fileName);
                restaurant.setDescription(description);
                restaurantRepository.save(restaurant);
                return ResponseEntity.status(HttpStatus.OK).body("Thêm nhà hàng thành công với id= "+ restaurant.getId());
            } catch (IOException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Loi: " + e.getMessage());
            }
        }
    }


    @Override
    public ArrayList<Restaurant> getAllRestaurant() {
        return new ArrayList<>(restaurantRepository.findAll());
    }

    @Override
    public ArrayList<Restaurant> findAllRestaurantByLocationAndPrice(String name, String location, String priceRestaurant) {
        Specification<Restaurant> spec = Specification.where(RestaurantSpecification.hasLocation(location))
                .and(RestaurantSpecification.hasName(name))
                .and(RestaurantSpecification.haspriceRestaurant(priceRestaurant));
        return new ArrayList<>(restaurantRepository.findAll(spec));
    }
    @Override
    public Page<Restaurant> findAllRestaurantByLocationAndPriceandNameForSearch(String name, String location, String priceRestaurant, Pageable pageable) {
        Specification<Restaurant> spec = Specification.where(RestaurantSpecification.hasLocation(location))
                .and(RestaurantSpecification.hasName(name))
                .and(RestaurantSpecification.haspriceRestaurant(priceRestaurant));
        return restaurantRepository.findAll(spec, pageable);
    }

    @Override
    public Restaurant updateRestaurant(Long id,String name,String location,BigDecimal price,
                             int availableTables,MultipartFile multipartFile, String description) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.getRestaurantById(id);
        if (!restaurantOptional.isPresent()) {
            throw new EntityNotFoundException("Không tồn tại nhà hàng với id: " + id);
        }
        else{
            if (multipartFile.isEmpty()) {
                throw new Error("File không được để trống");
            }
            try {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                Path uploadPath = Paths.get(UPLOAD_DIR + fileName);

                if (!Files.exists(uploadPath.getParent())) {
                    Files.createDirectories(uploadPath.getParent());
                }

                Files.copy(multipartFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
                Restaurant restaurant = restaurantOptional.get();
                restaurant.setName(name);
                restaurant.setLocation(location);
                restaurant.setPrice(price);
                restaurant.setAvailableTables(availableTables);
                restaurant.setImageRestaurant(fileName);
                restaurant.setDescription(description);
                restaurantRepository.save(restaurant);
                return restaurant;
            } catch (IOException e){
                System.out.println(e);
            }
        }
        return null;
    }

    @Override
    public String deleteRestaurant(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.getRestaurantById(id);
        if(restaurantOptional.isPresent()){
            restaurantRepository.deleteById(id);
            return "Xóa Thành công!";
        }
        throw new EntityNotFoundException("Không tồn tại nhà hàng với id: "+ id);
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findRestaurantById(id);
    }

}
