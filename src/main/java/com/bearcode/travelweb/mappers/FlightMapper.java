package com.bearcode.travelweb.mappers;


import com.bearcode.travelweb.DTO.FlightDTO;
import com.bearcode.travelweb.models.Flight;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class FlightMapper {
    private final ModelMapper modelMapper;
    public FlightMapper() {
        this.modelMapper = new ModelMapper();
    }

    public FlightDTO convertToDTO(Flight flight){
        return modelMapper.map(flight, FlightDTO.class);
    }

    public  Flight convertToEntity(FlightDTO flightDTO){
        return modelMapper.map(flightDTO, Flight.class);
    }
}
