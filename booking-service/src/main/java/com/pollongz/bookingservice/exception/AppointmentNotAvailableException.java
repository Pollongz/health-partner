package com.pollongz.bookingservice.exception;

public class AppointmentNotAvailableException extends RuntimeException {

    public AppointmentNotAvailableException(String message){
        super(message);
    }
}
