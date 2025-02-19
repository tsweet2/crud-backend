package com.example.crudbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.crudbackend.security.JwtUtil;
import com.example.crudbackend.model.User;
import com.example.crudbackend.model.dto.UserDTO;
import com.example.crudbackend.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;  // ✅ Use PasswordEncoder instead

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder; // ✅ Correctly inject PasswordEncoder
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmailAddress(user.getEmailAddress()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // ✅ Uses injected encoder
        user.setRole("USER");
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

@PostMapping("/login")
public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
    System.out.println("🔍 Received login request for email: " + userDTO.getEmailAddress());
    User user = userRepository.findByEmailAddress(userDTO.getEmailAddress())
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Compare hashed passwords
    if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    String token = jwtUtil.generateToken(user.getEmailAddress(), user.getRole());
    return ResponseEntity.ok(Collections.singletonMap("token", token));
}

}

