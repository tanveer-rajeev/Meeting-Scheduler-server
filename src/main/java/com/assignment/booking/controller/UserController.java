package com.assignment.booking.controller;

import com.assignment.booking.DTO.UserDTO;
import com.assignment.booking.entity.Booking;
import com.assignment.booking.entity.User;

import com.assignment.booking.response.UserResponse;
import com.assignment.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserDTO user) throws Exception {

        return userService.signUpUser(user);
    }

    @GetMapping("/allBookings/{username}")
    public List<Booking> getAllUserBookings(@PathVariable String username) {
        return userService.getAllUserBookings(username);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @PutMapping("/{userId}")
    public User updateUser(@RequestBody UserDTO userDTO,@PathVariable Integer userId){
         return userService.updateUser(userDTO,userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer userId){
        return userService.deleteUser(userId);
    }

//    @GetMapping("/logOut")
//    public void logout(){
////        System.out.println("hi");
////        Authentication authentication = SecurityContextHolder
////                .getContext().getAuthentication();
////        System.out.println(authentication.isAuthenticated());
////        authentication.setAuthenticated(false);
////        System.out.println(authentication.isAuthenticated());
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                   null , null, null);
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//    }
}
