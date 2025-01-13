package com.bearcode.travelweb.services.Impl;


import com.bearcode.travelweb.models.TableRestaurant;
import com.bearcode.travelweb.repositories.RestaurantRepository;
import com.bearcode.travelweb.repositories.TableRestaurantRepository;
import com.bearcode.travelweb.services.TableRestaurantService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TableRestaurantServiceImpl implements TableRestaurantService {

    private final String UPLOAD_DIR = "src/main/resources/static/uploadsTable/";

    @Autowired
    private TableRestaurantRepository tableRestaurantRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public ResponseEntity<String> addTableRestaurant(Long restaurantId, String tableRestaurantName, String tableRestaurantType, int area,
                                           BigDecimal price, String amenities,int quantity, MultipartFile multipartFile) {

        if(multipartFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Khong duoc de anh trong");
        }
        if(tableRestaurantRepository.existsByTableRestaurantNameAndAreaAndRestaurantId(tableRestaurantName, area, restaurantId)){
            return ResponseEntity.status(HttpStatus.OK).body("Bàn này đã tồn tại!");
        } else {
            try{
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                Path uploadPath = Paths.get(UPLOAD_DIR + fileName);

                if (!Files.exists(uploadPath.getParent())) {
                    Files.createDirectories(uploadPath.getParent());
                }

                Files.copy(multipartFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

                TableRestaurant tableRestaurant = new TableRestaurant();
                tableRestaurant.setTableRestaurantName(tableRestaurantName);
                tableRestaurant.setTableRestaurantType(tableRestaurantType);
                tableRestaurant.setArea(area);
                tableRestaurant.setAmenities(amenities);
                tableRestaurant.setPrice(price);
                tableRestaurant.setImageTable(fileName);
                tableRestaurant.setRestaurant(restaurantRepository.findRestaurantById(restaurantId));
                tableRestaurant.setQuantity(quantity);
                tableRestaurantRepository.save(tableRestaurant);
                return ResponseEntity.status(HttpStatus.OK).body("Thêm bàn thành công với id= "+ tableRestaurant.getId());
            } catch (IOException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Loi: " + e.getMessage());
            }
        }
    }

    @Override
    public ArrayList<TableRestaurant> getAllTableRestaurant(Long restaurantId) {
        return new ArrayList<>(tableRestaurantRepository.getAllByRestaurant_Id(restaurantId));
    }
    @Override
    public TableRestaurant updateTableRestaurant(Long idTableRestaurant, String tableRestaurantName, String tableRestaurantType, int area, BigDecimal price,
                             String amenities,int quantity, MultipartFile multipartFile) {
        Optional<TableRestaurant> tableRestaurantOptional = tableRestaurantRepository.getTableRestaurantById(idTableRestaurant);
        if (!tableRestaurantOptional.isPresent()) {
            throw new EntityNotFoundException("Không tồn tại bàn với id: " + idTableRestaurant);
        } else {
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
                TableRestaurant tableRestaurant = tableRestaurantOptional.get();
                tableRestaurant.setTableRestaurantName(tableRestaurantName);
                tableRestaurant.setTableRestaurantType(tableRestaurantType);
                tableRestaurant.setArea(area);
                tableRestaurant.setAmenities(amenities);
                tableRestaurant.setQuantity(quantity);
                tableRestaurant.setPrice(price);
                tableRestaurant.setImageTable(fileName);
                tableRestaurantRepository.save(tableRestaurant);
                return tableRestaurant;
            } catch (IOException e){
                System.out.println(e);
            }
        }
        return null;
    }

    @Override
    public String deleteTableRestaurant(Long id) {
        Optional<TableRestaurant> tableRestaurantOptional = tableRestaurantRepository.getTableRestaurantById(id);
        if(tableRestaurantOptional.isPresent()){
            tableRestaurantRepository.deleteById(id);
            return "Xóa thành công!";
        }
        throw new EntityNotFoundException("Không tồn tại bàn với id: "+ id);
    }

    @Override
    public TableRestaurant findTableRestaurantById(Long id) {
        return tableRestaurantRepository.findTableRestaurantById(id);
    }

    @Override
    public String updateQuantity(Long id, int quantity) {
        Optional<TableRestaurant> tableRestaurantOptional = tableRestaurantRepository.getTableRestaurantById(id);
        if (!tableRestaurantOptional.isPresent()) {
            throw new EntityNotFoundException("Không tồn tại bàn với id: " + id);
        } else {
            TableRestaurant tableRestaurant= tableRestaurantOptional.get();
            if(tableRestaurant.getQuantity() < quantity){
                return "Vượt quá số bàn còn tồn tại";
            }
            tableRestaurant.setQuantity(tableRestaurant.getQuantity()-quantity);
            tableRestaurantRepository.save(tableRestaurant);
            return "Cập nhật thành công";
        }
    }
}
