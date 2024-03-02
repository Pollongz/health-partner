package com.pollongz.bookingservice;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bookings")
class BookingController {

    private final BookingService bookingService;

    Logger LOG = LoggerFactory.getLogger(BookingController.class);

    @PostMapping
    void bookAppointment(@RequestBody Booking booking) {
        LOG.info("Reservation attempt occured at: {}", LocalDateTime.now());
        bookingService.bookAppointment(booking);

    }

}
