package com.bearcode.travelweb.mappers;

import com.bearcode.travelweb.DTO.TableRestaurantDTO;
import com.bearcode.travelweb.models.Restaurant;
import com.bearcode.travelweb.models.TableRestaurant;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class TableRestaurantMapper {
    private final ModelMapper modelMapper;

    public TableRestaurantMapper(){
        this.modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<TableRestaurantDTO, TableRestaurant>() {
            @Override
            protected void configure() {
                using(ctx -> {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setId((Long) ctx.getSource());
                    return restaurant;
                }).map(source.getRestaurantId()).setRestaurant(null);

            }
        });

        modelMapper.addMappings(new PropertyMap<TableRestaurant, TableRestaurantDTO>() {
            @Override
            protected void configure() {
                map(source.getRestaurant().getId(), destination.getRestaurantId());
            }
        });
    }

    public TableRestaurantDTO convertToDTO(TableRestaurant tableRestaurant){ return modelMapper.map(tableRestaurant, TableRestaurantDTO.class) ;}
    public TableRestaurant convertToEntity(TableRestaurantDTO tableRestaurantDTO){ return modelMapper.map(tableRestaurantDTO, TableRestaurant.class);}
}
