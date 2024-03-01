package com.pollongz.bookingservice.controller;

import com.pollongz.bookingservice.application.BookingService;
import com.pollongz.bookingservice.model.Booking;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    Logger LOG = LoggerFactory.getLogger(BookingController.class);

    @PostMapping
    public void bookAppointment(@RequestBody Booking booking) {
        LOG.info("Reservation attempt occured at: {}", LocalDateTime.now());
        bookingService.bookAppointment(booking);

    }

}
