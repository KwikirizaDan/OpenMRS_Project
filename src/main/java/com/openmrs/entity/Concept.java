package com.openmrs.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Concept Entity - Standardized medical terminology.
 * 
 * This is OpenMRS's most powerful feature. Instead of free-text
 * diagnoses, everything uses standardized concepts with codes
 * from LOINC, ICD-10, SNOMED CT.
 * 
 * Example: "Systolic Blood Pressure" has:
 * - LOINC code: 8480-6
 * - SNOMED CT: 271649006
 * - Unit: mmHg
 * - Datatype: NUMERIC
 */
@Entity
@Table(name = "concept")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conceptId;

    // Standard terminology codes
    private String loincCode;       // LOINC (labs, vitals)
    private String icd10Code;      // ICD-10 (diagnoses)
    private String snomedCtCode;    // SNOMED CT (clinical terms)

    // Human-readable name
    @Column(nullable = false)
    private String name;

    private String description;

    // How the value is stored
    @Enumerated(EnumType.STRING)
    private ConceptDatatype datatype;

    // Classification (vital sign, diagnosis, lab test, etc.)
    @ManyToOne
    @JoinColumn(name = "class_id")
    private ConceptClass conceptClass;

    // Unit of measurement (for numeric concepts)
    private String unit;

    // Normal ranges (for numeric concepts)
    private Double lowNormal;
    private Double hiNormal;

    public enum ConceptDatatype {
        NUMERIC,    // Numbers (BP, weight, temperature)
        CODED,      // Select from list (diagnosis, yes/no)
        TEXT,       // Free text (notes, comments)
        DATE,       // Dates (onset date, diagnosis date)
        BOOLEAN,    // True/False
        RULE        // Computed/derived
    }
}
