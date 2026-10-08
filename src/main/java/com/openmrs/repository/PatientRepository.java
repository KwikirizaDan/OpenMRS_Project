package com.openmrs.repository;

import com.openmrs.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByMedicalRecordNumber(String mrn);

    List<Patient> findByPersonGivenNameContainingIgnoreCase(String name);

    List<Patient> findByPersonFamilyNameContainingIgnoreCase(String name);

    // Search by name (first or last)
    @Query("SELECT p FROM Patient p WHERE " +
           "LOWER(p.person.givenName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(p.person.familyName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> searchByName(String name);

    boolean existsByMedicalRecordNumber(String mrn);
}
