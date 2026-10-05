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

import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.repository.SpecializationRepository;

@Service
public class SpecializationService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private SpecializationRepository specializationRepo;

    public String saveSpecialization(Specialization specialization) {
        specializationRepo.save(specialization);
        return "saved successfully";
    }

    public Specialization getSpecializationById(UUID id) {
        Optional<Specialization> specialization = specializationRepo.findById(id);
        if (specialization.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No specialization with id " + id);
        }
        return specialization.get();
    }

    public List<Specialization> getAllSpecializations() {
        return specializationRepo.findAll();
    }

    public String updateSpecialization(UUID id, Specialization updated) {
        Optional<Specialization> existing = specializationRepo.findById(id);
        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No specialization with id " + id);
        }
        Specialization specialization = existing.get();
        specialization.setName(updated.getName());
        specializationRepo.save(specialization);
        return "updated successfully";
    }

    public String deleteSpecialization(UUID id) {
        if (specializationRepo.existsById(id)) {
            specializationRepo.deleteById(id);
            return "Specialization with id " + id + " deleted successfully";
        }
        return "we don't have specialization with that id " + id;
    }

    public String assignDoctor(UUID specializationId, UUID doctorId) {
    Specialization specialization = getSpecializationById(specializationId);
    Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);
    if (doctorOpt.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No doctor with id " + doctorId);
    }
    Doctor doctor = doctorOpt.get();
    if (specialization.getDoctors().contains(doctor)) {
        return "Doctor already has this specialization";
    }
    specialization.getDoctors().add(doctor);
    specializationRepo.save(specialization);
    return "Doctor assigned to specialization successfully";
}

public String removeDoctor(UUID specializationId, UUID doctorId) {
    Specialization specialization = getSpecializationById(specializationId);
    Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);
    if (doctorOpt.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No doctor with id " + doctorId);
    }
    specialization.getDoctors().remove(doctorOpt.get());
    specializationRepo.save(specialization);
    return "Doctor removed from specialization successfully";
}

    public List<Specialization> getUnusedSpecializations() {
        return specializationRepo.findUnusedSpecializations();
    }
}
