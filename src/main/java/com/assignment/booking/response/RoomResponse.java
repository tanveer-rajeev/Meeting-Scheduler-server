package com.assignment.booking.response;

import lombok.Data;

@Data
public class RoomResponse {
    private String roomName;
    private Integer capacity;
    private String startTime;
    private String endTime;
}
