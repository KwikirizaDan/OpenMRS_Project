package com.openmrs.controller;

import com.openmrs.entity.*;
import com.openmrs.service.EncounterService;
import com.openmrs.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Encounter Resource - REST API for clinical encounters.
 * 
 * Endpoints:
 * - POST /api/encounter - Create new encounter
 * - GET /api/encounter/patient/{id} - Get patient's encounters
 * - POST /api/encounter/{id}/obs - Add observation
 * - GET /api/encounter/patient/{id}/obs - Get patient's observations
 */
@RestController
@RequestMapping("/api/encounter")
@RequiredArgsConstructor
public class EncounterController {

    private final EncounterService encounterService;
    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<Encounter> createEncounter(@RequestBody EncounterRequest request) {
        Patient patient = patientService.getPatient(request.getPatientId());
        Encounter encounter = encounterService.createEncounter(
                patient,
                request.getProvider(),
                request.getLocation(),
                request.getEncounterType()
        );
        return new ResponseEntity<>(encounter, HttpStatus.CREATED);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Encounter>> getPatientEncounters(@PathVariable Long patientId) {
        return ResponseEntity.ok(encounterService.getPatientEncounters(patientId));
    }

    @PostMapping("/{encounterId}/obs")
    public ResponseEntity<Observation> addObservation(
            @PathVariable Long encounterId,
            @RequestBody ObservationRequest request) {
        // Simplified - would need to fetch encounter and concept
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/patient/{patientId}/obs")
    public ResponseEntity<List<Observation>> getPatientObservations(@PathVariable Long patientId) {
        return ResponseEntity.ok(encounterService.getPatientObservations(patientId));
    }

    // DTOs for requests
    @lombok.Data
    public static class EncounterRequest {
        private Long patientId;
        private String provider;
        private String location;
        private Encounter.EncounterType encounterType;
    }

    @lombok.Data
    public static class ObservationRequest {
        private Long conceptId;
        private Double value;
    }
}
