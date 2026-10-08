package com.openmrs.service;

import com.openmrs.entity.*;
import com.openmrs.repository.EncounterRepository;
import com.openmrs.repository.ObservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EncounterService {

    private final EncounterRepository encounterRepository;
    private final ObservationRepository observationRepository;

    /**
     * Create a new clinical encounter (visit).
     */
    @Transactional
    public Encounter createEncounter(Patient patient, String provider, 
                                      String location, Encounter.EncounterType type) {
        Encounter encounter = Encounter.builder()
                .patient(patient)
                .provider(provider)
                .location(location)
                .encounterDatetime(LocalDateTime.now())
                .encounterType(type)
                .status(Encounter.EncounterStatus.ACTIVE)
                .dateCreated(LocalDateTime.now())
                .build();
        
        return encounterRepository.save(encounter);
    }

    /**
     * Add an observation (vital sign, symptom, etc.) to an encounter.
     */
    @Transactional
    public Observation addObservation(Encounter encounter, Concept concept, 
                                       Patient patient, Double value) {
        Observation obs = Observation.builder()
                .encounter(encounter)
                .concept(concept)
                .patient(patient)
                .obsDatetime(LocalDateTime.now())
                .valueNumeric(value)
                .status(Observation.ObservationStatus.FINAL)
                .dateCreated(LocalDateTime.now())
                .build();
        
        return observationRepository.save(obs);
    }

    /**
     * Get all encounters for a patient.
     */
    @Transactional(readOnly = true)
    public List<Encounter> getPatientEncounters(Long patientId) {
        return encounterRepository.findByPatientPatientId(patientId);
    }

    /**
     * Get all observations for a patient.
     */
    @Transactional(readOnly = true)
    public List<Observation> getPatientObservations(Long patientId) {
        return observationRepository.findByPatientPatientId(patientId);
    }
}
