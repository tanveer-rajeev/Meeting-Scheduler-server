package com.assignment.booking.security;

import com.assignment.booking.SpringApplicationContext;
import com.assignment.booking.entity.User;
import com.assignment.booking.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentLoggedInUser {


        public static User getUserEntity() {
            Authentication authentication = SecurityContextHolder
                    .getContext().getAuthentication();
            String username = authentication.getName();

            UserService employeeService =
                    (UserService) SpringApplicationContext.getBean("userServiceImplementation");
            return employeeService.getUserByName(username);

        }


}
