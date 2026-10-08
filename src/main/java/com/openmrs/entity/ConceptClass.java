package com.openmrs.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Concept Class - Categorizes concepts by type.
 * 
 * Examples:
 * - Vital Sign (BP, temperature, pulse)
 * - Diagnosis (hypertension, diabetes)
 * - Lab Test (CBC, glucose, HIV test)
 * - Procedure (surgery, vaccination)
 * - Question (symptoms, history)
 */
@Entity
@Table(name = "concept_class")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConceptClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;

    @Column(unique = true, nullable = false)
    private String name;  // e.g., "Vital Sign", "Diagnosis", "Lab Test"

    private String description;
}
