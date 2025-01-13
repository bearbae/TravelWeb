package com.bearcode.travelweb.mappers;

import com.bearcode.travelweb.DTO.AirportDTO;
import com.bearcode.travelweb.models.Airport;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper {

    private final ModelMapper modelMapper;

    public AirportMapper() {
        this.modelMapper = new ModelMapper();
    }

    // Chuyển từ Airport entity sang AirportDTO
    public AirportDTO convertToDTO(Airport airport) {
        return modelMapper.map(airport, AirportDTO.class);
    }

    // Chuyển từ AirportDTO sang Airport entity
    public Airport convertToEntity(AirportDTO airportDTO) {
        return modelMapper.map(airportDTO, Airport.class);
    }
}
