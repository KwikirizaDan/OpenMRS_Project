package com.openmrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OpenMRS Learning Project
 * 
 * This project demonstrates core OpenMRS concepts:
 * - Patient registration with unique MRN
 * - Encounter-based clinical documentation
 * - Concept dictionary for standardized terminology
 * - Observations for clinical measurements
 * - Role-based access control
 * - Audit trails for data integrity
 */
@SpringBootApplication
public class OpenMrsApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenMrsApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  OpenMRS Learning Project Running!");
        System.out.println("  API: http://localhost:8080/api");
        System.out.println("  H2 Console: http://localhost:8080/h2-console");
        System.out.println("========================================\n");
    }
}
