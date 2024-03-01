package com.pollongz.appointmentservice.application;

import com.pollongz.appointmentservice.exception.AppointmentNotFoundException;
import com.pollongz.appointmentservice.model.Appointment;
import com.pollongz.appointmentservice.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isEmpty()) {
            throw new AppointmentNotFoundException("No appointment with id " + id + " in the database.");
        }
        return appointment.get();
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public boolean isAppointmentAvailable(long id) {
        return appointmentRepository.findById(id)
                .map(this::isAppointmentAvailable)
                .orElse(false);
    }

    private boolean isAppointmentAvailable(Appointment appointment) {
        return appointment.isAvailable();
    }

    public void makeAppointmentUnavailable(long id) {
        appointmentRepository.findById(id)
                .ifPresent(this::makeAppointmentUnavailable);
    }

    public void makeAppointmentUnavailable(Appointment appointment) {
        appointment.setAvailable(false);
        appointmentRepository.save(appointment);
    }
}
