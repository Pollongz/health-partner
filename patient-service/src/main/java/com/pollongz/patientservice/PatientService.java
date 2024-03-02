package com.pollongz.patientservice;

import com.pollongz.patientservice.exception.PatientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class PatientService {

    private final PatientRepository patientRepository;

    List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    Patient getPatientById(long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new PatientNotFoundException("No patient with id " + id + " in the database.");
        }
        return patient.get();
    }

    Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    Patient updatePatient(long id, Patient updatedPatient) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent() && patient.get().getId() == id) {
            updatedPatient.setId(id);
            patientRepository.save(updatedPatient);
        }
        return updatedPatient;
    }

    void deletePatient(long id) {
        patientRepository.deleteById(id);
    }

    void increaseAbsenceCounter(long id) {
        patientRepository.findById(id)
                .ifPresent(this::addAbsence);
    }

    void addAbsence(Patient patient) {
        patient.setAbsenceCounter(patient.getAbsenceCounter() + 1);
        patient.setLastAbsenceDate(LocalDate.now());
        if (patient.getAbsenceCounter() > 2) {
            blockPatient(patient);
        }
    }

    private static void blockPatient(Patient patient) {
        patient.setBlocked(true);
    }

    int getLastAbsencePeriod(long id) {
        return patientRepository.findById(id)
                .map(this::checkLastAbsencePeriod)
                .orElse(0);
    }

    void checkAndResetAbsenceCounter(long id) {
        patientRepository.findById(id)
                .ifPresent(this::resetAbsences);
    }

    boolean isPatientBlocked(long id) {
        return patientRepository.findById(id)
                .map(this::isPatientBlocked)
                .orElse(false);
    }

    private boolean isPatientBlocked(Patient patient) {
        return patient.isBlocked();
    }

    private void resetAbsences(Patient patient) {
        if (checkLastAbsencePeriod(patient) < 6) {
            patient.setAbsenceCounter(0);
            unblockPatient(patient);
        }
    }

    private int checkLastAbsencePeriod(Patient patient) {
        return LocalDate.now().getMonthValue() - patient.getLastAbsenceDate().getMonthValue();
    }

    private static void unblockPatient(Patient patient) {
        patient.setBlocked(false);
    }
}



