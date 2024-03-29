package com.pollongz.bookingservice;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "bookings")
class Booking {

    @Id
    private String id;
    private long patientId;
    private long appointmentId;
    private LocalDateTime timestamp;

}
