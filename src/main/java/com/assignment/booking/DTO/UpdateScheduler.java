package com.assignment.booking.DTO;
import lombok.Data;

@Data
public class UpdateScheduler {
    private String roomName;
    private String bookingDate;
    private String startTime;
    private String endTime;
}
