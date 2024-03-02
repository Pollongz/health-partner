package com.pollongz.patientservice;

import com.pollongz.patientservice.exception.PatientNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;
    @InjectMocks
    private PatientService patientService;

    List<Optional<Patient>> patients;

    @BeforeEach
    void setUp() {
        Optional<Patient> patient1 = Optional.of(new Patient(1L,
                "Darek",
                "Test",
                97082914065L,
                2,
                LocalDate.now().minusDays(1),
                false
        ));
        Optional<Patient> patient2 = Optional.of(new Patient(2L,
                "Marek",
                "Test1",
                92082914056L,
                1,
                LocalDate.now().minusMonths(1),
                false
        ));
        patients = List.of(patient1,patient2);
    }

    @Test
    void getAllPatients() {
        List<Patient> patientsList = patients.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        when(patientRepository.findAll()).thenReturn(patientsList);

        List<Patient> allPatients = patientService.getAllPatients();

        assertThat(allPatients).isNotNull();
        assertThat(2).isEqualTo(allPatients.size());

        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void getPatientById() {
        Optional<Patient> patient1 = patients.getFirst();
        when(patientRepository.findById(1L)).thenReturn(patient1);

        assertThat(patient1).isPresent();
        assertThat(patientService.getPatientById(1L)).isEqualTo(patient1.get());

        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void getPatientById_PatientNotFoundException() {
        when(patientRepository.findById(3L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> patientService.getPatientById(3L))
                .isInstanceOf(PatientNotFoundException.class)
                .hasMessage("No patient with id " + 3L + " in the database.");

        verify(patientRepository, times(1)).findById(3L);
    }

    @Test
    void updatePatient() {
        Optional<Patient> patient1 = patients.getFirst();
        when(patientRepository.findById(1L)).thenReturn(patient1);

        Patient patientToUpdate = new Patient(1L,
                "Darek",
                "Test123",
                97082914065L,
                2,
                LocalDate.now().minusDays(1),
                false
        );

        assertThat(patient1).isPresent();
        Patient updatedPatient = patientService.updatePatient(1L, patientToUpdate);

        assertThat(updatedPatient.getLastName()).isEqualTo(patientToUpdate.getLastName());
    }

    @Test
    void addAbsence() {
        Optional<Patient> patient = patients.getLast();

        assertThat(patient).isPresent();
        assertThat(patient.get().getAbsenceCounter()).isEqualTo(1);

        patientService.addAbsence(patient.get());

        assertThat(patient.get().getAbsenceCounter()).isEqualTo(2);
    }


    @Test
    void addAbsence_blockPatient() {
        Optional<Patient> patient = patients.getFirst();

        assertThat(patient).isPresent();
        assertThat(patient.get().getAbsenceCounter()).isEqualTo(2);
        assertThat(patient.get().isBlocked()).isFalse();

        patientService.addAbsence(patient.get());

        assertThat(patient.get().getAbsenceCounter()).isEqualTo(3);
        assertThat(patient.get().isBlocked()).isTrue();
    }
}