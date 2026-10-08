package com.openmrs.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Observation Entity - A clinical finding or measurement.
 * 
 * Observations are the core clinical data in OpenMRS.
 * Every vital sign, symptom, diagnosis, lab result
 * is stored as an Observation linked to a Concept.
 * 
 * Example: BP reading of 120/80
 * - Concept: "Systolic Blood Pressure" (LOINC: 8480-6)
 * - Value: 120
 * - Unit: mmHg
 * - Encounter: Today's outpatient visit
 */
@Entity
@Table(name = "obs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long obsId;

    // What was observed (the concept)
    @ManyToOne
    @JoinColumn(name = "concept_id", nullable = false)
    private Concept concept;

    // Who it's about
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // When it was observed
    @Column(nullable = false)
    private LocalDateTime obsDatetime;

    // The value - stored differently based on concept datatype
    private String value;           // Generic string value
    private Double valueNumeric;     // For numeric concepts
    private String valueText;       // For text concepts
    private LocalDateTime valueDate; // For date concepts

    // For coded concepts (e.g., diagnosis)
    @ManyToOne
    @JoinColumn(name = "value_coded_id")
    private Concept valueCoded;

    // Which encounter this belongs to
    @ManyToOne
    @JoinColumn(name = "encounter_id")
    private Encounter encounter;

    // Status of the observation
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ObservationStatus status = ObservationStatus.FINAL;

    // Who recorded it
    private String recordedBy;

    // Audit trail
    private LocalDateTime dateCreated;

    public enum ObservationStatus {
        FINAL,          // Verified and complete
        PRELIMINARY,    // Awaiting confirmation
        CANCELLED,      // Entered in error
        ENTERED_IN_ERROR // Mistake, should not be used
    }
}
