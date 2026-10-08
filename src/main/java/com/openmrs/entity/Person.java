package com.openmrs.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Person Entity - Represents a human being in the system.
 * 
 * In OpenMRS, Person is separate from Patient because:
 * - One person can be a patient at multiple facilities
 * - A person can also be a user (doctor, nurse)
 * - Not all persons are patients (e.g., emergency contacts)
 */
@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @Column(nullable = false)
    private String givenName;       // First name

    private String middleName;

    @Column(nullable = false)
    private String familyName;      // Last name

    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    private String phoneNumber;

    private String email;

    // Audit trail - who created/modified this record
    private LocalDateTime dateCreated;
    private String createdBy;
    private LocalDateTime dateChanged;
    private String changedBy;

    public enum Gender {
        MALE, FEMALE, OTHER, UNKNOWN
    }
}
