package com.hardware.services.impl;

import com.hardware.dtos.UserDTO;
import com.hardware.entities.User;
import com.hardware.exceptions.ResourceNotFoundException;
import com.hardware.mapper.UserMapper;
import com.hardware.repositories.UserRepository;
import com.hardware.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // Create Users
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    // Get User By ID
    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("USER ID NOT FOUND"));
        return UserMapper.toDTO(user);
    }

    // Update User
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER ID NOT FOUND"));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(user);
        return UserMapper.toDTO(updatedUser);
    }

    // Delete User
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("USER ID NOT FOUND"));
        userRepository.delete(user);
    }

    // Get ALl Users
    @Override
    public List<UserDTO> getAllUsers() {
        List<User>  users = userRepository.findAll();
        return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }
}

