package com.assignment.booking.response;
import lombok.Data;


@Data
public class UserResponse {
    private Integer id;
    private String username;
    private String department;
    private String phoneNumber;
}
