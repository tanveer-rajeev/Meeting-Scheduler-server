package com.assignment.booking.controller;
import com.assignment.booking.entity.Booking;
import com.assignment.booking.entity.Room;
import com.assignment.booking.repository.RoomRepository;
import com.assignment.booking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository roomRepository;
    private final RoomService roomService;
    @Autowired
    public RoomController(RoomRepository roomRepository, RoomService roomService) {
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }
    @PostMapping
    public Room addRoom(@RequestBody Room room){
        return roomService.addRoom(room);
    }

    @PutMapping("/update/{roomId}")
    public Room updateRoom(@RequestBody Room room,@PathVariable Integer roomId){
       return roomService.updateRoom(room,roomId);
    }

    @GetMapping("/bookings/{roomId}")
    public List<Booking> getALlBookings(@PathVariable Integer roomId){
        return roomService.getAllBookings(roomId);
    }

    @GetMapping("/{roomName}")
    public Room getRoom(@PathVariable String roomName){
        return roomRepository.findByRoomName(roomName);
    }

    @GetMapping
    public List<Room> getAllRoom(){
        return roomRepository.findAll();
    }

    @DeleteMapping("/{roomId}")
    public void deleteRoom(@PathVariable Integer roomId){
        roomRepository.deleteById(roomId);
    }
}
