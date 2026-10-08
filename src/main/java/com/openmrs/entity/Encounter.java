package com.openmrs.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Encounter Entity - A clinical visit/interaction.
 * 
 * In OpenMRS, an encounter is the core clinical event.
 * It links together:
 * - Who (patient + provider)
 * - Where (location/facility)
 * - When (datetime)
 * - What type (outpatient, inpatient, emergency)
 * - What happened (observations, orders, diagnoses)
 */
@Entity
@Table(name = "encounter")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long encounterId;

    // Who was seen
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Who provided care
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private String provider;  // Could be a Provider entity

    // Where it happened
    private String location;

    // When it happened
    @Column(nullable = false)
    private LocalDateTime encounterDatetime;

    // Type of visit
    @Enumerated(EnumType.STRING)
    private EncounterType encounterType;

    // Clinical findings during this encounter
    @OneToMany(mappedBy = "encounter", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Observation> observations = new ArrayList<>();

    // Orders placed during this encounter
    @OneToMany(mappedBy = "encounter", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    // Status
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EncounterStatus status = EncounterStatus.ACTIVE;

    // Audit trail
    private LocalDateTime dateCreated;
    private String createdBy;

    public enum EncounterType {
        OUTPATIENT, INPATIENT, EMERGENCY, TELEMEDICINE, HOME_VISIT
    }

    public enum EncounterStatus {
        ACTIVE, VOIDED, ENTERED_IN_ERROR
    }
}
