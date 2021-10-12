package com.assignment.booking.service;

import com.assignment.booking.entity.Booking;
import com.assignment.booking.entity.Room;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomService {
    Room addRoom(Room room);
    Room updateRoom(Room room,Integer roomId);
    Room getRoomById(Integer room);
    List<Room> getAllRoom();
    List<Booking> getAllBookings(Integer id);
}
