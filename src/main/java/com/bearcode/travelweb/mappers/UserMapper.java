package com.bearcode.travelweb.mappers;

import com.bearcode.travelweb.DTO.UserDTO;
import com.bearcode.travelweb.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;
    public UserMapper() {
        this.modelMapper = new ModelMapper();
    }

    public UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
