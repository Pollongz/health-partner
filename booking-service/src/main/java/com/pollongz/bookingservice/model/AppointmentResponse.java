package com.pollongz.bookingservice.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponse {

    private long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String facility;
    private LocalDateTime visitDate;
    private boolean isAvailable;
}
