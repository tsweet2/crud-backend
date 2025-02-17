package com.example.crudbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private String role;
}
