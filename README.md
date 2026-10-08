# OpenMRS Learning Project

A simplified OpenMRS-style EMR system for learning healthcare software development.

## What is OpenMRS?

OpenMRS (Open Medical Record System) is a free, open-source electronic medical record platform used in 40+ countries. It's the standard EMR for resource-constrained healthcare environments.

## Why Learn OpenMRS?

1. **Global Health Standard** - Used by NGOs, governments, and hospitals worldwide
2. **Healthcare Interoperability** - HL7, FHIR, LOINC, ICD-10, SNOMED CT
3. **Clinical Workflows** - Encounter-based documentation, concept dictionaries
4. **Real-World Scale** - Millions of patients, multiple facilities, offline capability

## Project Structure

```
OpenMRS_Project/
├── src/main/java/com/openmrs/
│   ├── entity/           # Core entities
│   │   ├── Person.java       # Human being (demographics)
│   │   ├── Patient.java      # Enrolled patient (MRN, status)
│   │   ├── Concept.java      # Standardized medical term
│   │   ├── ConceptClass.java # Concept category
│   │   ├── Encounter.java    # Clinical visit
│   │   ├── Observation.java  # Clinical finding/measurement
│   │   ├── Order.java        # Lab/prescription/procedure order
│   │   ├── User.java         # System user
│   │   └── Role.java         # Role with permissions
│   ├── repository/       # Data access
│   ├── service/          # Business logic
│   ├── controller/       # REST API
│   └── config/           # Data initialization
```

## Key OpenMRS Concepts Demonstrated

### 1. Person vs Patient
- **Person** = Human being (name, DOB, gender, address)
- **Patient** = Enrolled patient (MRN, status, facility)
- One Person can be a Patient at multiple facilities

### 2. Concept Dictionary
Standardized medical terminology with codes:
- **LOINC**: Lab tests and vital signs (e.g., 8480-6 = Systolic BP)
- **ICD-10**: Diagnoses (e.g., I10 = Hypertension)
- **SNOMED CT**: Clinical terms (e.g., 38341003 = Hypertension)

### 3. Encounter Model
Every clinical interaction is an Encounter:
- Links Patient + Provider + Location + Time
- Contains Observations (findings) and Orders (actions)

### 4. Observations
Clinical measurements linked to Concepts:
- Vital signs (BP, temperature, pulse)
- Symptoms and findings
- Lab results

### 5. Role-Based Access Control
- **Physician**: Full clinical access
- **Nurse**: View + add observations
- **Data Clerk**: Patient registration only

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/patient` | Register patient |
| GET | `/api/patient/{id}` | Get patient |
| GET | `/api/patient/mrn/{mrn}` | Get by MRN |
| GET | `/api/patient/search?name=john` | Search patients |
| PUT | `/api/patient/{id}` | Update patient |
| DELETE | `/api/patient/{id}` | Deactivate patient |
| POST | `/api/encounter` | Create encounter |
| GET | `/api/encounter/patient/{id}` | Get patient encounters |
| GET | `/api/encounter/patient/{id}/obs` | Get observations |

## How to Run

```bash
mvn spring-boot:row
```

Access:
- API: http://localhost:8080/api
- H2 Console: http://localhost:8080/h2-console

## Next Steps

1. Add FHIR API support
2. Implement HL7 message handling
3. Add reporting module
4. Implement offline sync
5. Add multi-facility support
