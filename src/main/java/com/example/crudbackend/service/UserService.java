package com.example.crudbackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.example.crudbackend.model.User;
import com.example.crudbackend.model.dto.UserDTO;
import com.example.crudbackend.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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
        return new UserDTO(user.getUserID(), user.getLastName(), user.getFirstName(), user.getPhoneNumber(), user.getEmailAddress(), user.getPassword(), user.getRole());
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User();
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmailAddress(userDTO.getEmailAddress());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : "USER");
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser.getUserID(), savedUser.getLastName(), savedUser.getFirstName(), savedUser.getPhoneNumber(), savedUser.getEmailAddress(), savedUser.getPassword(), savedUser.getRole());
    }

    public UserDTO updateUser(Long id, UserDTO updatedUserDTO) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isPresent()) {
            User user = existingUserOpt.get();
            user.setFirstName(updatedUserDTO.getFirstName());
            user.setLastName(updatedUserDTO.getLastName());
            user.setPhoneNumber(updatedUserDTO.getPhoneNumber());
            user.setEmailAddress(updatedUserDTO.getEmailAddress());
            user.setPassword(updatedUserDTO.getPassword());
            user.setRole(updatedUserDTO.getRole());
            userRepository.save(user);

            if(updatedUserDTO.getPassword() != null && !updatedUserDTO.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));
            }

            if(updatedUserDTO.getRole() != null) {
                user.setRole(updatedUserDTO.getRole());
            }

            userRepository.save(user);

            System.out.println("✅ User updated: " + user.getFirstName() + " " + user.getLastName());
            return convertToDTO(user);
        } else {
            System.out.println("❌ User with ID " + id + " not found.");
            return null;
        }
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getUserID(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmailAddress(), user.getPassword(), user.getRole());
    }

    private User convertToEntity(UserDTO userDTO) {
        return new User(userDTO.getUserID(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber(), userDTO.getEmailAddress(), userDTO.getPassword(), userDTO.getRole());
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}
