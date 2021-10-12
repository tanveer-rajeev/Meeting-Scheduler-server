package com.assignment.booking.Exception;

import com.assignment.booking.DTO.BookedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BookedNotificationHandler extends RuntimeException {

    private static final long serialVersionUID = 1L;
    @Autowired
    private BookedInfo bookedInfo;

    public BookedNotificationHandler(String message,BookedInfo bookingInformation) {
        super(message);
        this.bookedInfo = bookingInformation;
    }

    public BookedInfo getBookedInfo() {
        return bookedInfo;
    }

    public void setBookedInfo(BookedInfo bookedInfo) {
        this.bookedInfo = bookedInfo;
    }
}
