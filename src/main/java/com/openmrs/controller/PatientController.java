package com.openmrs.controller;

import com.openmrs.entity.Patient;
import com.openmrs.entity.Person;
import com.openmrs.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Patient Resource - REST API for patient management.
 * 
 * OpenMRS-style endpoints:
 * - POST /api/patient - Register new patient
 * - GET /api/patient/{id} - Get patient by ID
 * - GET /api/patient?mrn=MRN-2024-001234 - Get by MRN
 * - GET /api/patient/search?name=john - Search patients
 * - PUT /api/patient/{id} - Update patient
 * - DELETE /api/patient/{id} - Deactivate patient
 */
@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> registerPatient(@RequestBody Person person) {
        Patient patient = patientService.registerPatient(person);
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatient(id));
    }

    @GetMapping("/mrn/{mrn}")
    public ResponseEntity<Patient> getByMRN(@PathVariable String mrn) {
        return ResponseEntity.ok(patientService.getByMRN(mrn));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatients(@RequestParam String name) {
        return ResponseEntity.ok(patientService.searchPatients(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Person person) {
        return ResponseEntity.ok(patientService.updatePatient(id, person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivatePatient(@PathVariable Long id) {
        patientService.deactivatePatient(id);
        return ResponseEntity.noContent().build();
    }
}
