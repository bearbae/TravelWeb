package com.bearcode.travelweb.services.Impl;


import com.bearcode.travelweb.DTO.RoomDTO;
import com.bearcode.travelweb.mappers.RoomMapper;
import com.bearcode.travelweb.models.Hotel;
import com.bearcode.travelweb.models.Room;
import com.bearcode.travelweb.repositories.HotelRepository;
import com.bearcode.travelweb.repositories.RoomRepository;
import com.bearcode.travelweb.services.RoomService;
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
public class RoomServiceImpl implements RoomService {


    private final String UPLOAD_DIR = "src/main/resources/static/uploadsRoom/";

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;



    @Override
    public ResponseEntity<String> addRoom(Long hotelId, String roomName, String roomType, String view, int area,
                                          BigDecimal pricePerNight, String amenities,int quantity, MultipartFile multipartFile) {

        if(multipartFile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Khoong duoc de anh trong");
        }
        if(roomRepository.existsByRoomNameAndAreaAndHotelId(roomName, area, hotelId)){
            return ResponseEntity.status(HttpStatus.OK).body("Phòng này đã tồn tại!");
        } else {
            try{
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                Path uploadPath = Paths.get(UPLOAD_DIR + fileName);

                if (!Files.exists(uploadPath.getParent())) {
                    Files.createDirectories(uploadPath.getParent());
                }

                Files.copy(multipartFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

                Room room = new Room();
                room.setRoomName(roomName);
                room.setRoomType(roomType);
                room.setView(view);
                room.setArea(area);
                room.setAmenities(amenities);
                room.setPricePerNight(pricePerNight);
                room.setImageRoom(fileName);
                room.setHotel(hotelRepository.findHotelById(hotelId));
                room.setQuantity(quantity);
                roomRepository.save(room);
                return ResponseEntity.status(HttpStatus.OK).body("Thêm phong thành công với id= "+ room.getId());
            } catch (IOException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Loi: " + e.getMessage());
            }
        }
    }

    @Override
    public ArrayList<Room> getAllRoom(Long hotelId) {
        return new ArrayList<>(roomRepository.getAllByHotel_Id(hotelId));
    }




    @Override
    public Room updateRoom(Long idRoom, String roomName, String roomType, String view, int area, BigDecimal pricePerNight,
                           String amenities,int quantity, MultipartFile multipartFile) {
//        if(roomRepository.existsByRoomNameAndAreaAndHotelId(roomName, area, roomRepository.findRoomById(idRoom).getHotel().getId())){
//            throw new EntityNotFoundException("Phòng này đã tồn tại");
//        } else {
            Optional<Room> roomOptional = roomRepository.getRoomById(idRoom);
            if (!roomOptional.isPresent()) {
                throw new EntityNotFoundException("Không tồn tại phong với id: " + idRoom);
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
                    Room room = roomOptional.get();
                    room.setRoomName(roomName);
                    room.setRoomType(roomType);
                    room.setView(view);
                    room.setArea(area);
                    room.setAmenities(amenities);
                    room.setQuantity(quantity);
                    room.setPricePerNight(pricePerNight);
                    room.setImageRoom(fileName);
                    roomRepository.save(room);
                    return room;
                } catch (IOException e){
                    System.out.println(e);
                }
            }
        return null;
    }

    @Override
    public String deleteRoom(Long id) {
        Optional<Room> roomOptional = roomRepository.getRoomById(id);
        if(roomOptional.isPresent()){
            roomRepository.deleteById(id);
            return "Xóa thành công!";
        }
        throw new EntityNotFoundException("Không tồn tại phòng với id: "+ id);
    }

    @Override
    public Room findRoomById(Long id) {
        return roomRepository.findRoomById(id);
    }

    @Override
    public String updateQuantitty(Long id, int quantity) {
        Optional<Room> roomOptional = roomRepository.getRoomById(id);
        if (!roomOptional.isPresent()) {
            throw new EntityNotFoundException("Không tồn tại phong với id: " + id);
        } else {
            Room room= roomOptional.get();
            if(room.getQuantity() < quantity){
                return "Vượt quá số phòng còn tồn tại";
            }
            room.setQuantity(room.getQuantity()-quantity);
            roomRepository.save(room);
            return "Cập nhật thành công";
        }
    }
}
