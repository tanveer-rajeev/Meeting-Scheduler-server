package com.assignment.booking.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiException {

    private Date timestamp;
    private String message;
    private String details;


}
