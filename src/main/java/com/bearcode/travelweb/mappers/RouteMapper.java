package com.bearcode.travelweb.mappers;

import com.bearcode.travelweb.DTO.RouteDTO;
import com.bearcode.travelweb.models.Route;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {
    private final ModelMapper modelMapper;

    public RouteMapper() {
        this.modelMapper = new ModelMapper();
    }

    // Convert Route entity to RouteDTO
    public RouteDTO convertToDTO(Route route) {
        return modelMapper.map(route, RouteDTO.class);
    }

    // Convert RouteDTO to Route entity
    public Route convertToEntity(RouteDTO routeDTO) {
        return modelMapper.map(routeDTO, Route.class);
    }
}
