package com.bearcode.travelweb.mappers;


import com.bearcode.travelweb.DTO.RestaurantDTO;
import com.bearcode.travelweb.models.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {
    private final ModelMapper modelMapper;

    public RestaurantMapper(){
        this.modelMapper = new ModelMapper();
    }

    public RestaurantDTO convertToDTO(Restaurant restaurant){ return modelMapper.map(restaurant, RestaurantDTO.class) ;}
    public Restaurant convertToEntity(RestaurantDTO restaurantDTO){ return modelMapper.map(restaurantDTO, Restaurant.class);}

}
