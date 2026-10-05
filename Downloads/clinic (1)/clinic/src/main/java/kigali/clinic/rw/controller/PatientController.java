package kigali.clinic.rw.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.service.PatientService;
import kigali.clinic.rw.service.SpecializationService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private SpecializationService specializationService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> savePatient(@RequestBody Patient patient) {
        return new ResponseEntity<>(patientService.savePatient(patient), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable UUID id, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.updatePatient(id, patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.deletePatient(id));
    }

           @GetMapping("/by-last-name")
       public ResponseEntity<List<Patient>> getByLastName(@RequestParam String lastName) {
           return ResponseEntity.ok(patientService.getPatientsByLastName(lastName));
       }
           @GetMapping("/unused")
    public ResponseEntity<List<Specialization>> getUnused() {
        return ResponseEntity.ok(specializationService.getUnusedSpecializations());
    }

           @GetMapping("/of-doctor/{doctorId}")
       public ResponseEntity<?> getPatientsOfDoctor(@PathVariable UUID doctorId) {
           Optional<List<Patient>> patients = patientService.getPatientsOfDoctor(doctorId);
           if (patients.isEmpty()) {
               return new ResponseEntity<>("The doctor with that id does not exist", HttpStatus.NOT_FOUND);
           }
           return ResponseEntity.ok(patients.get());
       }
    }
