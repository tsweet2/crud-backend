package com.example.crudbackend.controller;

import com.example.crudbackend.model.User;
import com.example.crudbackend.model.dto.UserDTO;
import com.example.crudbackend.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers() { return userService.getAllUsers(); }

    @GetMapping("/id")
    public UserDTO getUserById(@PathVariable Long id) { return userService.getUserById(id); }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) { return userService.saveUser(userDTO); }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    return ResponseEntity.noContent().build();  // ✅ 204 No Content on success
    }
}