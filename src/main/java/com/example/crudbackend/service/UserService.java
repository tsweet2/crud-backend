package com.example.crudbackend.service;

import com.example.crudbackend.model.User;
import com.example.crudbackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }
    public User getUserById(Long id) { return userRepository.findById(id).orElse(null); }
    public User saveUser(User user) { return userRepository.save(user); }
    public void deleteUser(Long id) { userRepository.deleteById(id); }
    
    @PostConstruct
    public void initDatabase() {
        if (userRepository.count() == 0) {
            userRepository.save(new User(null, "Doe", "John", "1234567890", "john.doe@example.com"));
            userRepository.save(new User(null, "Smith", "Jane", "0987654321", "jane.smith@example.com"));
        }
    }
}
