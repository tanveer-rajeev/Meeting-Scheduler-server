package com.assignment.booking.DTO;

import com.assignment.booking.entity.User;
import com.assignment.booking.response.RoomResponse;
import com.assignment.booking.response.UserResponse;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@ToString
public class BookedInfo {

    private List<UserResponse> bookedPersonsList;
    private Set<RoomResponse> roomList;
    private boolean trigger = false;

}
