package com.pollongz.patientservice;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "patients")
class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private long pesel;
    private int absenceCounter;
    private LocalDate lastAbsenceDate;
    private boolean isBlocked;
}
