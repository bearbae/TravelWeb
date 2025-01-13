package com.bearcode.travelweb.mappers;


import com.bearcode.travelweb.DTO.HotelDTO;
import com.bearcode.travelweb.models.Hotel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {
    private final ModelMapper modelMapper;

    public HotelMapper(){
        this.modelMapper = new ModelMapper();
    }

    public HotelDTO convertToDTO(Hotel hotel){ return modelMapper.map(hotel, HotelDTO.class) ;}
    public Hotel convertToEntity(HotelDTO hotelDTO){ return modelMapper.map(hotelDTO, Hotel.class);}

}
