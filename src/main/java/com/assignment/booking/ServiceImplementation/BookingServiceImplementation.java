package com.assignment.booking.ServiceImplementation;

import com.assignment.booking.DTO.BookingDTO;
import com.assignment.booking.DTO.UpdateScheduler;
import com.assignment.booking.Exception.BookedNotificationHandler;
import com.assignment.booking.Exception.ResourceNotFoundException;
import com.assignment.booking.DTO.BookedInfo;
import com.assignment.booking.entity.Booking;
import com.assignment.booking.entity.Room;
import com.assignment.booking.entity.User;
import com.assignment.booking.repository.BookingRepository;
import com.assignment.booking.repository.RoomRepository;
import com.assignment.booking.repository.UserRepository;
import com.assignment.booking.response.RoomResponse;
import com.assignment.booking.response.UserResponse;
import com.assignment.booking.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImplementation implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImplementation(BookingRepository bookingRepository, RoomRepository roomRepository,
                                        ModelMapper modelMapper, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public RoomResponse getRoomByBookingId(Integer bookingId) {
        Room room = getBookingById(bookingId).getRoom();
        return modelMapper.map(room, RoomResponse.class);
    }

    @Override
    public List<Booking> getALlBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public ResponseEntity<?> updateBooking(UpdateScheduler bookingRequest, Integer bookingId) {

        Room requestedRoom = roomRepository.findByRoomName(bookingRequest.getRoomName());
        if (hourParse(requestedRoom.getStartTime()) > hourParse(bookingRequest.getStartTime())
                || hourParse(requestedRoom.getEndTime()) < hourParse(bookingRequest.getEndTime())) {
            return new ResponseEntity<>("Given start time or end time is not in the requested room time range." +
                    " Try to give time in the range ", HttpStatus.ACCEPTED);
        }
        List<Booking> nonOverLapBookingList = requestedRoom.getBooking().stream()
                .filter(booking -> booking.getBookingDate().equals(bookingRequest.getBookingDate()))
                .collect(Collectors.toList());

        if (nonOverLapBookingList.isEmpty() ||
                isBookingTimeSlotTaken(requestedRoom, modelMapper.map(bookingRequest, Booking.class)))
            bookingRepository.findById(bookingId).map(booking -> {
                booking.setBookingDate(bookingRequest.getBookingDate());
                booking.setStartTime(bookingRequest.getStartTime());
                booking.setEndTime(bookingRequest.getEndTime());
                return bookingRepository.save(booking);
            }).orElseThrow(() -> new ResourceNotFoundException("Booking id is not in the system"));

        return new ResponseEntity<>("Time slot already taken. Try another room", HttpStatus.ACCEPTED);
    }

    @Override
    public Booking getBookingById(Integer id) {
        return bookingRepository
                .findById(id)
                .stream()
                .filter(booking -> booking
                        .getId()
                        .equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + id));
    }

    @Override
    public UserResponse getUsrByBookingId(Integer bookingId) {
        User user = getBookingById(bookingId).getUser();
        return modelMapper.map(user, UserResponse.class);
    }


    @Override
    public ResponseEntity<?> makeBooking(BookingDTO bookingDTO, String userName, Integer roomId) throws ParseException {
        Booking bookingRequest = modelMapper.map(bookingDTO, Booking.class);

        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new ResourceNotFoundException("User not available in the system");
        }

        if (checkValidationOfBookingDate(bookingRequest.getBookingDate())) {
            throw new ResourceNotFoundException("- Booking date not valid " + bookingRequest.getBookingDate());
        }

        Room requestedRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room does not exist "));

        List<Booking> nonOverLapBookingList = requestedRoom.getBooking().stream()
                .filter(booking -> booking.getBookingDate().equals(bookingRequest.getBookingDate()))
                .collect(Collectors.toList());

        if (hourParse(requestedRoom.getStartTime()) > hourParse(bookingRequest.getStartTime())
                || hourParse(requestedRoom.getEndTime()) < hourParse(bookingRequest.getStartTime())
                || hourParse(requestedRoom.getStartTime()) > hourParse(bookingRequest.getEndTime())
                || hourParse(requestedRoom.getEndTime()) < hourParse(bookingRequest.getEndTime())) {
            return new ResponseEntity<>("Given start time or end time is not in the requested room time range ", HttpStatus.ACCEPTED);
        }
        if (nonOverLapBookingList.isEmpty() || isBookingTimeSlotTaken(requestedRoom, bookingRequest)) {
            bookingRequest.setRoom(requestedRoom);
            bookingRequest.setUser(user);
            Booking bookingConfirmation = bookingRepository.save(bookingRequest);
            return new ResponseEntity<>(bookingConfirmation, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Time slot already taken . Try another room", HttpStatus.ACCEPTED);
    }

    public int getCapacityFreeWorkingPlace(String requestedBookingDate) throws ParseException {

        if (checkValidationOfBookingDate(requestedBookingDate)) {
            throw new ResourceNotFoundException("- Booking date not valid " + requestedBookingDate);
        }

        List<Room> roomList = roomRepository.findAll();
        double numberOfWorkingPlace = 0;
        double currentRoomCapacity = 0;

        for (Room bookedRoom : roomList) {

            List<Booking> bookingList = bookedRoom.getBooking();

            numberOfWorkingPlace += bookedRoom.getCapacity();


            for (Booking booked : bookingList) {
                if (booked
                        .getBookingDate()
                        .equals(requestedBookingDate)) {
                    currentRoomCapacity++;
                }
            }
        }
        double result = (numberOfWorkingPlace - currentRoomCapacity) / numberOfWorkingPlace;

        return (int) Math.round(result * 100);
    }


    public boolean isBookingTimeSlotTaken(Room room, Booking requestedBooking) {

        // check if the requested booking start time and end time overlap with the requested room start time and end time
        int requestStartTime = hourParse(requestedBooking.getStartTime());
        int requestEndTime = hourParse(requestedBooking.getEndTime());

        // check time schedule of the booking request is in the time range of requested room that already predefine (9:00 to 5:00)

        List<Booking> bookingList = room.getBooking().stream()
                .filter(booking -> booking.getBookingDate().equals(requestedBooking.getBookingDate()))
                .collect(Collectors.toList());

        for (Booking booking : bookingList) {
            int startTime = hourParse(booking.getStartTime());
            int endTime = hourParse(booking.getEndTime());

            if (startTime == requestEndTime
                    && (minParse(requestedBooking.getEndTime()) > minParse(booking.getStartTime()))) {
                return false;
            }
            if (startTime <= requestStartTime && endTime >= requestEndTime) {
                return false;
            }
        }

        return true;
    }

    private int hourParse(String time) {
        if (time.length() == 4) return Integer.parseInt(time.substring(0, 1));
        return Integer.parseInt(time.substring(0, 2));
    }

    private int minParse(String time) {
        if (time.length() == 4) return Integer.parseInt(time.substring(2));
        return Integer.parseInt(time.substring(3));
    }

    private boolean checkValidationOfBookingDate(String requestBookingDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate = sdf.parse(requestBookingDate);
        Date currentDate = sdf.parse(String.valueOf(LocalDate.now()));

        return requestDate.getTime() < currentDate.getTime();
    }


}
