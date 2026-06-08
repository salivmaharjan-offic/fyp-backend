package com.hardware.services;
import com.hardware.dtos.UserDTO;

import java.util.List;

public interface UserService {
    // Create User - Method
    UserDTO createUser(UserDTO userDTO);

    // Get User By ID
    UserDTO getUserById(Long id);

    // Update User
    UserDTO updateUser(Long id, UserDTO userDTO);

    // Delete User
    void deleteUser(Long id);

    // Get All Users
    List<UserDTO> getAllUsers();
}
