package com.pollongz.appointmentservice;

import com.pollongz.appointmentservice.exception.AppointmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    Appointment getAppointmentById(long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isEmpty()) {
            throw new AppointmentNotFoundException("No appointment with id " + id + " in the database.");
        }
        return appointment.get();
    }

    Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    boolean isAppointmentAvailable(long id) {
        return appointmentRepository.findById(id)
                .map(this::isAppointmentAvailable)
                .orElse(false);
    }

    private boolean isAppointmentAvailable(Appointment appointment) {
        return appointment.isAvailable();
    }

    void makeAppointmentUnavailable(long id) {
        appointmentRepository.findById(id)
                .ifPresent(this::makeAppointmentUnavailable);
    }

    private void makeAppointmentUnavailable(Appointment appointment) {
        appointment.setAvailable(false);
        appointmentRepository.save(appointment);
    }
}
