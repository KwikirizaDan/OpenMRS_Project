package com.openmrs.repository;

import com.openmrs.entity.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, Long> {

    Optional<Concept> findByLoincCode(String loincCode);

    Optional<Concept> findByIcd10Code(String icd10Code);

    List<Concept> findByNameContainingIgnoreCase(String name);

    List<Concept> findByConceptClassClassId(Long classId);
}
