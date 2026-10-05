package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.repository.DoctorRepository;
import kigali.clinic.rw.repository.OfficeRepository;
import kigali.clinic.rw.domain.Office;
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private OfficeRepository officeRepo;

        public String saveDoctor(Doctor doctor, Integer officeNumber) {
        if (officeNumber != null) {
            Optional<Office> office = officeRepo.findByOfficeNumber(officeNumber);
            if (office.isEmpty()) {
                return "Office with number " + officeNumber + " does not exist.";
            }
            if (doctorRepo.existsByOffice(office.get())) {
                return "This office is already assigned to another doctor.";
            }
            doctor.setOffice(office.get());
        }
        doctorRepo.save(doctor);
        return "saved successfully";
    }

    public Doctor getDoctorById(UUID id) {
        Optional<Doctor> doctor = doctorRepo.findById(id);
        if (doctor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No doctor with id " + id);
        }
        return doctor.get();
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public String updateDoctor(UUID id, Doctor updatedDoctor) {
        Optional<Doctor> existing = doctorRepo.findById(id);
        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No doctor with id " + id);
        }
        Doctor doctor = existing.get();
        doctor.setFirstName(updatedDoctor.getFirstName());
        doctor.setLastName(updatedDoctor.getLastName());
        doctor.setDateOfBirth(updatedDoctor.getDateOfBirth());
        doctorRepo.save(doctor);
        return "updated successfully";
    }

    public String deleteDoctor(UUID id) {
        if (doctorRepo.existsById(id)) {
            doctorRepo.deleteById(id);
            return "Doctor with id " + id + " deleted successfully";
        }
        return "we don't have doctor with that id " + id;
    }
        public List<Doctor> getDoctorsBySpecialization(String name) {
        return doctorRepo.findDoctorsBySpecializationName(name);
    }
        public List<Doctor> getDoctorsWithoutOffice() {
        return doctorRepo.findDoctorsWithoutOffice();
    }
}
