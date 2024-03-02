package com.pollongz.appointmentservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class AppointmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(AppointmentRepository appointmentRepository) {
		return args -> {
			Appointment appointment1 = new Appointment();
			appointment1.setFirstName("Lekarz1");
			appointment1.setLastName("Lekarz12");
			appointment1.setSpecialization("Psycholog");
			appointment1.setFacility("ul.Słoneczna 8, 82-220 Elbląg");
			appointment1.setVisitDate(LocalDateTime.now().plusDays(10));
			appointment1.setAvailable(true);

			Appointment appointment2 = new Appointment();
			appointment2.setFirstName("Lekarz1");
			appointment2.setLastName("Lekarz12");
			appointment2.setSpecialization("Psycholog");
			appointment2.setFacility("ul.Słoneczna 8, 82-220 Elbląg");
			appointment2.setVisitDate(LocalDateTime.now().plusDays(10).plusHours(1));
			appointment2.setAvailable(true);

			Appointment appointment3 = new Appointment();
			appointment3.setFirstName("Lekarz1");
			appointment3.setLastName("Lekarz12");
			appointment3.setSpecialization("Psycholog");
			appointment3.setFacility("ul.Słoneczna 8, 82-220 Elbląg");
			appointment3.setVisitDate(LocalDateTime.now().plusDays(10).plusHours(2));
			appointment3.setAvailable(false);

			appointmentRepository.save(appointment1);
			appointmentRepository.save(appointment2);
			appointmentRepository.save(appointment3);
		};
	}
}
