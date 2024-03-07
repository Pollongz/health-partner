package com.pollongz.appointmentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/appointments")
class AppointmentController {

    private final AppointmentService appointmentService;
    private final KafkaProducer kafkaProducer;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getPatientById(@PathVariable long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment != null) {
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        String visitNotification = "NEW VISIT DATE AVAILABLE\n"
                + "Specialization: " + createdAppointment.getSpecialization()
                + "\nDate: " + createdAppointment.getVisitDate()
                + "\nAddress: " + createdAppointment.getFacility()
                + "\nVisit health-partner.com to book an appointment!";
        kafkaProducer.sendMessage(visitNotification, createdAppointment.getSpecialization());
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/check-status")
    public boolean checkIfAppointmentIsAvailable(@PathVariable long id) {
        return appointmentService.isAppointmentAvailable(id);
    }


    @PostMapping("/{id}/block-appointment")
    public void makeAppointmentUnavailable(@PathVariable long id) {
        appointmentService.makeAppointmentUnavailable(id);
    }
}
