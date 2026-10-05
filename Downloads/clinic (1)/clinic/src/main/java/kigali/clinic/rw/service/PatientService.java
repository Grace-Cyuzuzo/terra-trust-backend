package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
   import kigali.clinic.rw.repository.DoctorRepository;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
       private DoctorRepository doctorRepo;

    public String savePatient(Patient patient) {
        patientRepo.save(patient);
        return "saved successfully";
    }

    public Patient getPatientById(UUID id) {
        Optional<Patient> patient = patientRepo.findById(id);
        if (patient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No patient with id " + id);
        }
        return patient.get();
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public String updatePatient(UUID id, Patient updatedPatient) {
        Optional<Patient> existing = patientRepo.findById(id);
        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No patient with id " + id);
        }
        Patient patient = existing.get();
        patient.setFirstName(updatedPatient.getFirstName());
        patient.setLastName(updatedPatient.getLastName());
        patient.setDateOfBirth(updatedPatient.getDateOfBirth());
        patientRepo.save(patient);
        return "updated successfully";
    }

    public String deletePatient(UUID id) {
        if (patientRepo.existsById(id)) {
            patientRepo.deleteById(id);
            return "Patient with id " + id + " deleted successfully";
        }
        return "we don't have patient with that id " + id;
    }

        public List<Patient> getPatientsByLastName(String lastName) {
        return patientRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
           

              public Optional<List<Patient>> getPatientsOfDoctor(UUID doctorId) {
           if (!doctorRepo.existsById(doctorId)) {
               return Optional.empty();
           }
           return Optional.of(patientRepo.findPatientsByDoctorId(doctorId));
       }
}