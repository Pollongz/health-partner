package com.pollongz.patientservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/patients")
class PatientController {

    private final PatientService patientService;

    @GetMapping
    private ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Patient> getPatientById(@PathVariable long id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    private ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Patient> updatePatient(@PathVariable long id, @RequestBody Patient updatedPatient) {
        Patient patient = patientService.updatePatient(id, updatedPatient);
        if (patient != null) {
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deletePatient(@PathVariable long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>("Patient succesfully deleted.", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/add-absence")
    private void increaseAbsenceCounter(@PathVariable long id) {
        patientService.increaseAbsenceCounter(id);
    }

    @GetMapping("/{id}/absence-period")
    private int getLastAbsencePeriod(@PathVariable long id) {
        return patientService.getLastAbsencePeriod(id);
    }

    @GetMapping("/{id}/reset-absence")
    private void checkAndResetAbsenceCounter(@PathVariable long id) {
        patientService.checkAndResetAbsenceCounter(id);
    }

    @GetMapping("/{id}/check-status")
    private boolean checkIfPatientIsBlocked(@PathVariable long id) {
        return patientService.isPatientBlocked(id);
    }

}
