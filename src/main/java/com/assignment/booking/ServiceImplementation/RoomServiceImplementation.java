package com.assignment.booking.ServiceImplementation;

import com.assignment.booking.Exception.ResourceNotFoundException;
import com.assignment.booking.entity.Booking;
import com.assignment.booking.entity.Room;
import com.assignment.booking.repository.RoomRepository;
import com.assignment.booking.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImplementation implements RoomService {

    final private RoomRepository roomRepository;

    public RoomServiceImplementation(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Room roomRequest, Integer roomId) {

        return roomRepository.findById(roomId).map(room -> {
            room.setRoomName(roomRequest.getRoomName());
            room.setCapacity(roomRequest.getCapacity());
            room.setStartTime(roomRequest.getStartTime());
            room.setEndTime(roomRequest.getEndTime());
            return roomRepository.save(room);
        }).orElseThrow(() -> new ResourceNotFoundException("Room id not found"));
    }

    @Override
    public Room getRoomById(Integer roomId) {
        return roomRepository.findById(roomId)
                .stream()
                .filter(room -> room.getId().equals(roomId))
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }


    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public List<Booking> getAllBookings(Integer id) {
        return getRoomById(id).getBooking();
    }

    public Room findRoomByRoomName(String name) {
        return roomRepository.findByRoomName(name);
    }
}
