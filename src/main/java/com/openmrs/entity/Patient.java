package com.openmrs.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Patient Entity - A Person enrolled in the healthcare system.
 * 
 * Key OpenMRS concept: Patient is separate from Person.
 * - Person = human being (demographics)
 * - Patient = enrolled patient (MRN, status, facility)
 */
@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    // Unique Medical Record Number - format: MRN-YYYY-XXXXXX
    @Column(unique = true, nullable = false)
    private String medicalRecordNumber;

    // Link to the Person record
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    // Patient status in the system
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PatientStatus status = PatientStatus.ACTIVE;

    // Facility where patient is registered
    private String facility;

    // Audit trail
    private LocalDateTime dateCreated;
    private String createdBy;
    private LocalDateTime dateChanged;
    private String changedBy;

    public enum PatientStatus {
        ACTIVE, INACTIVE, DECEASED, TRANSFERRED
    }
}
