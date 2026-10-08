package com.openmrs.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Role Entity - Defines a set of permissions.
 * 
 * OpenMRS uses role-based access control (RBAC).
 * Users are assigned roles, and roles have permissions.
 * 
 * Example: "Physician" role has permissions:
 * - View Patient, Edit Patient, Add Encounter
 * - Add Observation, Add Order, View Lab Results
 */
@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(unique = true, nullable = false)
    private String role;  // e.g., "Physician", "Nurse", "Admin"

    private String description;

    // Permissions are stored as strings
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "permission")
    @Builder.Default
    private Set<String> permissions = new HashSet<>();
}
