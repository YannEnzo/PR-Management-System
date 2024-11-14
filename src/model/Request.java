/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Request {
    private int requestId;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private LocalDateTime timeOfRequest;
    private String roomNumber;
    private String requestDescription;
    
    public Request(int requestId, String name, String gender, LocalDate dateOfBirth, String phoneNumber, String email, LocalDateTime timeOfRequest, String roomNumber, String requestDescription) {
        this.requestId = requestId;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.timeOfRequest = timeOfRequest;
        this.roomNumber = roomNumber;
        this.requestDescription = requestDescription;
    }
    public Request( String name, String gender, LocalDate dateOfBirth, String phoneNumber, String email, LocalDateTime timeOfRequest, String roomNumber, String requestDescription) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.timeOfRequest = timeOfRequest;
        this.roomNumber = roomNumber;
        this.requestDescription = requestDescription;
    }
    // Getters and setters for all properties

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getTimeOfRequest() {
        return timeOfRequest;
    }

    public void setTimeOfRequest(LocalDateTime timeOfRequest) {
        this.timeOfRequest = timeOfRequest;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }
    
}
