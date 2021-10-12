package com.assignment.booking.controller;

import com.assignment.booking.DTO.BookingDTO;

import com.assignment.booking.DTO.UpdateScheduler;
import com.assignment.booking.entity.Booking;
import com.assignment.booking.entity.Room;
import com.assignment.booking.repository.BookingRepository;
import com.assignment.booking.repository.RoomRepository;
import com.assignment.booking.response.RoomResponse;
import com.assignment.booking.response.UserResponse;
import com.assignment.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;

    }

    @GetMapping
    public List<Booking> getAllBooking(){
        return bookingService.getALlBookings();
    }

    @GetMapping("room/{bookingId}")
    public RoomResponse getRoomByBookingId(@PathVariable Integer bookingId){
        return bookingService.getRoomByBookingId(bookingId);
    }

    @PostMapping("userName/{userName}/roomId/{roomId}")
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO booking, @PathVariable String userName,
                                           @PathVariable Integer roomId) throws
            ParseException {
        return bookingService.makeBooking(booking, userName, roomId);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBooking(@RequestBody UpdateScheduler booking, @PathVariable Integer bookingId) {
        return bookingService.updateBooking(booking, bookingId);
    }

    @GetMapping("user/bookingId/{bookingId}")
    public UserResponse getUserByBookingId(@PathVariable Integer bookingId){
        return bookingService.getUsrByBookingId(bookingId);
    }

    @GetMapping("/getCapacity/{date}")
    public int getAllFreeCapacity(@PathVariable String date) throws ParseException {
        return bookingService.getCapacityFreeWorkingPlace(date);
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable Integer bookingId) {
        bookingRepository.deleteById(bookingId);
    }

}
