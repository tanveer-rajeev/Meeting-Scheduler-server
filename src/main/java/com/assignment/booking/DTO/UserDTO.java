package com.assignment.booking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    private String department;
    private String phoneNumber;
}
