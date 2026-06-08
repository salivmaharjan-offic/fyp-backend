package com.hardware.mapper;

import com.hardware.dtos.UserDTO;
import com.hardware.entities.User;

public class UserMapper {

    // User Entity - User DTO Convert
    public static UserDTO toDTO (User user){
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getAuthProvider()
        );
    }

    // User DTO - User Entity Convert
    public static User toEntity(UserDTO userDTO){
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getRole(),
                userDTO.getAuthProvider()
        );
    }
}
