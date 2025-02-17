package com.example.crudbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.example.crudbackend.model.User;
import com.example.crudbackend.model.dto.UserDTO;
import com.example.crudbackend.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(user -> modelMapper.map(user, UserDTO.class))
            .collect(Collectors.toList());
    }
    
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getUserID(), user.getLastName(), user.getFirstName(), user.getPhoneNumber(), user.getEmailAddress());
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User();
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmailAddress(userDTO.getEmailAddress());
    
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser.getUserID(), savedUser.getLastName(), savedUser.getFirstName(), savedUser.getPhoneNumber(), savedUser.getEmailAddress());
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}
