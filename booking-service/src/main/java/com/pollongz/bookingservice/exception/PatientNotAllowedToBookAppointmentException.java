package com.pollongz.bookingservice.exception;

public class PatientNotAllowedToBookAppointmentException extends RuntimeException {

    public PatientNotAllowedToBookAppointmentException(String message){
        super(message);
    }
}
