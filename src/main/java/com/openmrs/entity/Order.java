package com.openmrs.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Order Entity - A clinical order (lab test, prescription, procedure).
 * 
 * Orders represent actions to be taken:
 * - Lab orders: "Order CBC for patient X"
 * - Drug orders: "Prescribe Amoxicillin 500mg"
 * - Procedure orders: "Schedule chest X-ray"
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // What is being ordered
    @ManyToOne
    @JoinColumn(name = "concept_id", nullable = false)
    private Concept concept;

    // For whom
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Which encounter this order belongs to
    @ManyToOne
    @JoinColumn(name = "encounter_id")
    private Encounter encounter;

    // Type of order
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    // Order details
    private String instructions;     // Special instructions
    private String frequency;        // e.g., "twice daily"
    private String duration;         // e.g., "7 days"
    private Double quantity;         // For medications

    // Status
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;

    // Who ordered it
    private String orderedBy;

    // When
    private LocalDateTime orderDatetime;
    private LocalDateTime dateCreated;

    public enum OrderType {
        LAB, MEDICATION, PROCEDURE, RADIOLOGY, REFERRAL
    }

    public enum OrderStatus {
        NEW, ACTIVE, COMPLETED, CANCELLED, EXPIRED, DISCONTINUED
    }
}
