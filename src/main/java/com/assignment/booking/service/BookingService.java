package com.assignment.booking.service;

import com.assignment.booking.DTO.BookingDTO;
import com.assignment.booking.DTO.UpdateScheduler;
import com.assignment.booking.entity.Booking;
import com.assignment.booking.response.RoomResponse;
import com.assignment.booking.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.ParseException;
import java.util.List;

public interface BookingService {
    public RoomResponse getRoomByBookingId( Integer bookingId);
    List<Booking> getALlBookings();
    ResponseEntity<?> updateBooking(UpdateScheduler booking, Integer bookingId);
    Booking getBookingById(Integer id);
    UserResponse getUsrByBookingId(Integer bookingId);
    ResponseEntity<?> makeBooking(BookingDTO booking,String userName, Integer roomId) throws ParseException;
    int getCapacityFreeWorkingPlace(String requestedBookingDate) throws ParseException;
}
