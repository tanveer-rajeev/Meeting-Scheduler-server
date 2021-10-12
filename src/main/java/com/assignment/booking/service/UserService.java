package com.assignment.booking.service;


import com.assignment.booking.DTO.UserDTO;
import com.assignment.booking.entity.Booking;
import com.assignment.booking.entity.User;

import com.assignment.booking.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    List<Booking> getAllUserBookings(String username);
    User getUserById(Integer integer);
    User getUserByName(String name);
    UserResponse signUpUser(UserDTO user) throws Exception;
    User updateUser(UserDTO user,Integer userId);
    ResponseEntity<HttpStatus> deleteUser(Integer userId);
}
