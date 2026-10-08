package com.openmrs.repository;

import com.openmrs.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {

    List<Observation> findByPatientPatientId(Long patientId);

    List<Observation> findByConceptConceptId(Long conceptId);

    List<Observation> findByEncounterEncounterId(Long encounterId);
}
