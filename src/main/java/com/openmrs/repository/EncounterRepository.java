package com.openmrs.repository;

import com.openmrs.entity.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {

    List<Encounter> findByPatientPatientId(Long patientId);

    List<Encounter> findByEncounterDatetimeBetween(LocalDateTime start, LocalDateTime end);

    List<Encounter> findByEncounterType(Encounter.EncounterType type);
}
