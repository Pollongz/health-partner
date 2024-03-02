package com.pollongz.appointmentservice;

import org.springframework.data.jpa.repository.JpaRepository;

interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
