package com.openmrs.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User Entity - System user with roles and permissions.
 * 
 * OpenMRS has granular role-based access control.
 * Each role has specific permissions that determine
 * what the user can see and do.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;  // BCrypt hashed

    @Column(unique = true, nullable = false)
    private String email;

    // Link to person record
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    // Roles determine permissions
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    private boolean enabled = true;

    // Audit
    private LocalDateTime dateCreated;
    private LocalDateTime lastLogin;
}
