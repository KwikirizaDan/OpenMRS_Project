package com.openmrs.service;

import com.openmrs.entity.Patient;
import com.openmrs.entity.Person;
import com.openmrs.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;
    private final AtomicLong mrnCounter = new AtomicLong(1000);

    /**
     * Register a new patient with auto-generated MRN.
     * MRN format: MRN-YYYY-XXXXXX
     */
    @Transactional
    public Patient registerPatient(Person person) {
        String mrn = generateMRN();
        
        Patient patient = Patient.builder()
                .medicalRecordNumber(mrn)
                .person(person)
                .status(Patient.PatientStatus.ACTIVE)
                .dateCreated(LocalDateTime.now())
                .build();
        
        return patientRepository.save(patient);
    }

    /**
     * Generate unique Medical Record Number.
     */
    private String generateMRN() {
        String year = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"));
        long seq = mrnCounter.incrementAndGet();
        return String.format("MRN-%s-%06d", year, seq);
    }

    @Transactional(readOnly = true)
    public Patient getPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    @Transactional(readOnly = true)
    public Patient getByMRN(String mrn) {
        return patientRepository.findByMedicalRecordNumber(mrn)
                .orElseThrow(() -> new RuntimeException("Patient not found with MRN: " + mrn));
    }

    @Transactional(readOnly = true)
    public List<Patient> searchPatients(String name) {
        return patientRepository.searchByName(name);
    }

    @Transactional
    public Patient updatePatient(Long id, Person updatedPerson) {
        Patient patient = getPatient(id);
        // Update person details
        patient.setPerson(updatedPerson);
        patient.setDateChanged(LocalDateTime.now());
        return patientRepository.save(patient);
    }

    @Transactional
    public void deactivatePatient(Long id) {
        Patient patient = getPatient(id);
        patient.setStatus(Patient.PatientStatus.INACTIVE);
        patient.setDateChanged(LocalDateTime.now());
        patientRepository.save(patient);
    }
}
