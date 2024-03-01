package com.pollongz.patientservice.application;

import com.pollongz.patientservice.exception.PatientNotFoundException;
import com.pollongz.patientservice.model.Patient;
import com.pollongz.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new PatientNotFoundException("No patient with id " + id + " in the database.");
        }
        return patient.get();
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(long id, Patient updatedPatient) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent() && patient.get().getId() == id) {
            updatedPatient.setId(id);
            patientRepository.save(updatedPatient);
        }
        return updatedPatient;
    }

    public void deletePatient(long id) {
        patientRepository.deleteById(id);
    }

    public void increaseAbsenceCounter(long id) {
        patientRepository.findById(id)
                .ifPresent(this::addAbsence);
    }

    public void addAbsence(Patient patient) {
        patient.setAbsenceCounter(patient.getAbsenceCounter() + 1);
        patient.setLastAbsenceDate(LocalDate.now());
        if (patient.getAbsenceCounter() > 2) {
            blockPatient(patient);
        }
    }

    private static void blockPatient(Patient patient) {
        patient.setBlocked(true);
    }

    public int getLastAbsencePeriod(long id) {
        return patientRepository.findById(id)
                .map(this::checkLastAbsencePeriod)
                .orElse(0);
    }

    public void checkAndResetAbsenceCounter(long id) {
        patientRepository.findById(id)
                .ifPresent(this::resetAbsences);
    }

    public boolean isPatientBlocked(long id) {
        return patientRepository.findById(id)
                .map(this::isPatientBlocked)
                .orElse(false);
    }

    private boolean isPatientBlocked(Patient patient) {
        return patient.isBlocked();
    }

    public void resetAbsences(Patient patient) {
        if (checkLastAbsencePeriod(patient) < 6) {
            patient.setAbsenceCounter(0);
            unblockPatient(patient);
        }
    }

    public int checkLastAbsencePeriod(Patient patient) {
        return LocalDate.now().getMonthValue() - patient.getLastAbsenceDate().getMonthValue();
    }

    private static void unblockPatient(Patient patient) {
        patient.setBlocked(false);
    }
}



