package com.openmrs.config;

import com.openmrs.entity.*;
import com.openmrs.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

/**
 * Initializes sample data demonstrating OpenMRS concepts:
 * - Concept dictionary with LOINC/ICD-10 codes
 * - Person and Patient separation
 * - Encounter with observations
 * - Role-based users
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ConceptRepository conceptRepository;
    private final ConceptClassRepository conceptClassRepository;
    private final PatientRepository patientRepository;
    private final EncounterRepository encounterRepository;
    private final ObservationRepository observationRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        System.out.println("\n========================================");
        System.out.println("  Initializing OpenMRS Learning Data...");
        System.out.println("========================================\n");

        // 1. Create Concept Classes
        ConceptClass vitalSign = ConceptClass.builder()
                .name("Vital Sign")
                .description("Vital signs and measurements")
                .build();
        conceptClassRepository.save(vitalSign);

        ConceptClass diagnosis = ConceptClass.builder()
                .name("Diagnosis")
                .description("Diagnoses and conditions")
                .build();
        conceptClassRepository.save(diagnosis);

        // 2. Create Concepts with standard codes
        Concept systolicBP = Concept.builder()
                .name("Systolic Blood Pressure")
                .loincCode("8480-6")
                .snomedCtCode("271649006")
                .datatype(Concept.ConceptDatatype.NUMERIC)
                .conceptClass(vitalSign)
                .unit("mmHg")
                .lowNormal(90.0)
                .hiNormal(140.0)
                .build();
        conceptRepository.save(systolicBP);

        Concept diastolicBP = Concept.builder()
                .name("Diastolic Blood Pressure")
                .loincCode("8462-4")
                .snomedCtCode("271650006")
                .datatype(Concept.ConceptDatatype.NUMERIC)
                .conceptClass(vitalSign)
                .unit("mmHg")
                .lowNormal(60.0)
                .hiNormal(90.0)
                .build();
        conceptRepository.save(diastolicBP);

        Concept hypertension = Concept.builder()
                .name("Hypertension")
                .icd10Code("I10")
                .snomedCtCode("38341003")
                .datatype(Concept.ConceptDatatype.CODED)
                .conceptClass(diagnosis)
                .build();
        conceptRepository.save(hypertension);

        // 3. Create a Person and Patient
        Person person = Person.builder()
                .givenName("Alice")
                .familyName("Williams")
                .birthdate(LocalDate.of(1990, 5, 15))
                .gender(Person.Gender.FEMALE)
                .address("123 Main St, City")
                .phoneNumber("+1122334455")
                .email("alice@example.com")
                .dateCreated(java.time.LocalDateTime.now())
                .build();

        Patient patient = Patient.builder()
                .medicalRecordNumber("MRN-2026-001001")
                .person(person)
                .status(Patient.PatientStatus.ACTIVE)
                .facility("Central Hospital")
                .dateCreated(java.time.LocalDateTime.now())
                .build();
        patientRepository.save(patient);

        // 4. Create an Encounter with Observations
        Encounter encounter = Encounter.builder()
                .patient(patient)
                .provider("Dr. Smith")
                .location("Outpatient Clinic")
                .encounterDatetime(java.time.LocalDateTime.now())
                .encounterType(Encounter.EncounterType.OUTPATIENT)
                .status(Encounter.EncounterStatus.ACTIVE)
                .dateCreated(java.time.LocalDateTime.now())
                .build();
        encounterRepository.save(encounter);

        // 5. Add Observations (BP reading)
        Observation bp1 = Observation.builder()
                .encounter(encounter)
                .concept(systolicBP)
                .patient(patient)
                .obsDatetime(java.time.LocalDateTime.now())
                .valueNumeric(145.0)
                .status(Observation.ObservationStatus.FINAL)
                .recordedBy("Dr. Smith")
                .dateCreated(java.time.LocalDateTime.now())
                .build();
        observationRepository.save(bp1);

        Observation bp2 = Observation.builder()
                .encounter(encounter)
                .concept(diastolicBP)
                .patient(patient)
                .obsDatetime(java.time.LocalDateTime.now())
                .valueNumeric(92.0)
                .status(Observation.ObservationStatus.FINAL)
                .recordedBy("Dr. Smith")
                .dateCreated(java.time.LocalDateTime.now())
                .build();
                observationRepository.save(bp2);

        // 6. Create Roles
        Role physician = Role.builder()
                .role("Physician")
                .description("Medical doctor")
                .permissions(Set.of(
                        "View Patient", "Edit Patient", "Add Encounter",
                        "Add Observation", "Add Order", "View Lab Results"
                ))
                .build();
        roleRepository.save(physician);

        Role nurse = Role.builder()
                .role("Nurse")
                .description("Registered nurse")
                .permissions(Set.of(
                        "View Patient", "Add Encounter", "Add Observation",
                        "View Lab Results"
                ))
                .build();
        roleRepository.save(nurse);

        System.out.println("  Concepts: 3 (Systolic BP, Diastolic BP, Hypertension)");
        System.out.println("  Patients: 1 (Alice Williams, MRN-2026-001001)");
        System.out.println("  Encounters: 1 (Outpatient visit)");
        System.out.println("  Observations: 2 (BP: 145/92 mmHg)");
        System.out.println("  Roles: 2 (Physician, Nurse)");
        System.out.println("\n========================================");
        System.out.println("  OpenMRS Learning Data Loaded!");
        System.out.println("========================================\n");
    }
}
