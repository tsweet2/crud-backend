package com.example.crudbackend.model.dto;

public class UserDTO {
    private Long userID;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String emailAddress;

    public UserDTO() {}

    public UserDTO(Long userID, String lastName, String firstName, String phoneNumber, String emailAddress) {
        this.userID = userID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public Long getUserID() { return userID; }
    public void setUserID(Long userID) { this.userID = userID; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
}
