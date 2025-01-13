package com.bearcode.travelweb.mappers;

import com.bearcode.travelweb.DTO.RoomDTO;
import com.bearcode.travelweb.models.Hotel;
import com.bearcode.travelweb.models.Room;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    private final ModelMapper modelMapper;

    public RoomMapper(){
        this.modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<RoomDTO, Room>() {
            @Override
            protected void configure() {
                // Khi ánh xạ từ RoomDTO sang Room, tạo một đối tượng Hotel và gán cho biến hotel
                using(ctx -> {
                    Hotel hotel = new Hotel();
                    hotel.setId((Long) ctx.getSource());
                    return hotel;
                }).map(source.getHotelId()).setHotel(null); // setHotel(null) để đảm bảo không có giá trị khác ảnh hưởng

            }
        });

        // Ánh xạ từ Room -> RoomDTO
        modelMapper.addMappings(new PropertyMap<Room, RoomDTO>() {
            @Override
            protected void configure() {
                // Khi ánh xạ từ Room sang RoomDTO, lấy id từ đối tượng Hotel
                map(source.getHotel().getId(), destination.getHotelId());
            }
        });
    }

    public RoomDTO convertToDTO(Room room){ return modelMapper.map(room, RoomDTO.class) ;}
    public Room convertToEntity(RoomDTO roomDTO){ return modelMapper.map(roomDTO, Room.class);}
}
