package com.assignment.booking.repository;

import com.assignment.booking.entity.Booking;
import com.assignment.booking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Booking findByRoom(Room room);
    Booking findByBookingDate(String bookingDate);
}
