package com.assignment.booking.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookingDTO {
    private String bookingDate;
    private String startTime;
    private String endTime;
}
