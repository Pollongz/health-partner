package com.pollongz.bookingservice.model;

import lombok.Data;

@Data
public class PatientResponse {

    private long id;
    private String firstName;
    private String lastName;
    private long pesel;
    private int absenceCounter;
    private boolean isBlocked;
}
