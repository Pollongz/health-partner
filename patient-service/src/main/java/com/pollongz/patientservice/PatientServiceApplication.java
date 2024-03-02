package com.pollongz.patientservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PatientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(PatientRepository patientRepository) {
		return args -> {
			Patient patient1 = new Patient();
			patient1.setFirstName("Paweł");
			patient1.setLastName("Kowczar");
			patient1.setPesel(97082714098L);
			patient1.setAbsenceCounter(0);
			patient1.setBlocked(false);

			Patient patient2 = new Patient();
			patient2.setFirstName("Marcin");
			patient2.setLastName("Poler");
			patient2.setPesel(94031698033L);
			patient2.setAbsenceCounter(1);
			patient2.setBlocked(false);

			Patient patient3 = new Patient();
			patient3.setFirstName("Adam");
			patient3.setLastName("Bowdar");
			patient3.setPesel(93022556063L);
			patient3.setAbsenceCounter(2);
			patient3.setBlocked(false);

			Patient patient4 = new Patient();
			patient4.setFirstName("Kamila");
			patient4.setLastName("Warun");
			patient4.setPesel(91013434053L);
			patient4.setAbsenceCounter(3);
			patient4.setBlocked(true);

			patientRepository.save(patient1);
			patientRepository.save(patient2);
			patientRepository.save(patient3);
			patientRepository.save(patient4);
		};
	}
}
