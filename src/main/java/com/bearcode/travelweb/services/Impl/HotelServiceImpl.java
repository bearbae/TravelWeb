package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.api.HotelSpecification;
import com.bearcode.travelweb.mappers.HotelMapper;
import com.bearcode.travelweb.models.Hotel;
import com.bearcode.travelweb.repositories.HotelRepository;
import com.bearcode.travelweb.services.HotelService;
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
public class HotelServiceImpl implements HotelService {

    private final String UPLOAD_DIR = "src/main/resources/static/uploadsHotel/";

    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private HotelRepository hotelRepository;

    // Thêm mới khách sạn
    @Override
    public ResponseEntity<String> addnewHotel(String name,String location,BigDecimal price,
                                              int availableRooms,MultipartFile multipartFile, String description){
        if(multipartFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Khoong duoc de anh trong");
        }
        if(hotelRepository.existsByLocation(location) && hotelRepository.existsByName(name)){
            return ResponseEntity.status(HttpStatus.OK).body("Khách sạn đã tồn tại!");
        } else {
            try{
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                Path uploadPath = Paths.get(UPLOAD_DIR + fileName);

                if (!Files.exists(uploadPath.getParent())) {
                    Files.createDirectories(uploadPath.getParent());
                }

                Files.copy(multipartFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

                Hotel hotel = new Hotel();
                hotel.setName(name);
                hotel.setLocation(location);
                hotel.setPrice(price);
                hotel.setAvailableRooms(availableRooms);
                hotel.setImageHotel(fileName);
                hotel.setDescription(description);
                hotelRepository.save(hotel);
                return ResponseEntity.status(HttpStatus.OK).body("Thêm khách sạn thành công với id= "+ hotel.getId());
            } catch (IOException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Loi: " + e.getMessage());
            }
        }
    }

    // Lấy thông tin tất cả khách sạn

    @Override
    public ArrayList<Hotel> getAllHotel() {
        return new ArrayList<>(hotelRepository.findAll());
    }

    @Override
    public ArrayList<Hotel> findAllHotelByLocationAndPrice(String name, String location, String priceHotel) {
        Specification<Hotel> spec = Specification.where(HotelSpecification.hasLocation(location))
                .and(HotelSpecification.hasName(name))
                .and(HotelSpecification.haspriceHotel(priceHotel));
        return new ArrayList<>(hotelRepository.findAll(spec));
    }
        @Override
        public Page<Hotel> findAllHotelByLocationAndPriceandNameForSearch(String name, String location, String priceHotel, Pageable pageable) {
            Specification<Hotel> spec = Specification.where(HotelSpecification.hasLocation(location))
                    .and(HotelSpecification.hasName(name))
                    .and(HotelSpecification.haspriceHotel(priceHotel));
            return hotelRepository.findAll(spec, pageable);
        }


    // Cập nhật thông tin khách sạn
    @Override
    public Hotel updateHotel(Long id,String name,String location,BigDecimal price,
                                int availableRooms,MultipartFile multipartFile, String description) {
            Optional<Hotel> hotelOptional = hotelRepository.getHotelById(id);
            if (!hotelOptional.isPresent()) {
                throw new EntityNotFoundException("Không tồn tại khách sạn với id: " + id);
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
                    Hotel hotel = hotelOptional.get();
                    hotel.setName(name);
                    hotel.setLocation(location);
                    hotel.setPrice(price);
                    hotel.setAvailableRooms(availableRooms);
                    hotel.setImageHotel(fileName);
                    hotel.setDescription(description);
                    hotelRepository.save(hotel);
                    return hotel;
                } catch (IOException e){
                    System.out.println(e);
                }
            }
            return null;
        }

    @Override
    public String deleteHotel(Long id) {
        Optional<Hotel> hotelOptional = hotelRepository.getHotelById(id);
        if(hotelOptional.isPresent()){
           hotelRepository.deleteById(id);
           return "Xóa Thành công!";
        }
        throw new EntityNotFoundException("Không tồn tại khách sạn với id: "+ id);
    }

    @Override
    public Hotel findHotelById(Long id) {
        return hotelRepository.findHotelById(id);
    }

//    @Override
//    public Page<Hotel> getAllHotel(Pageable pageable) {
//        return hotelRepository.findAll(pageable);
//    }

}
