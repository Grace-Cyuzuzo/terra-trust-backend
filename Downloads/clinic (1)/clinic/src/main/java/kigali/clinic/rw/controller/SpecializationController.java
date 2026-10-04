package kigali.clinic.rw.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.service.SpecializationService;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveSpecialization(@RequestBody Specialization specialization) {
        return new ResponseEntity<>(specializationService.saveSpecialization(specialization), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialization> getSpecialization(@PathVariable UUID id) {
        return ResponseEntity.ok(specializationService.getSpecializationById(id));
    }

    @GetMapping
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        return ResponseEntity.ok(specializationService.getAllSpecializations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSpecialization(@PathVariable UUID id, @RequestBody Specialization specialization) {
        return ResponseEntity.ok(specializationService.updateSpecialization(id, specialization));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpecialization(@PathVariable UUID id) {
        return ResponseEntity.ok(specializationService.deleteSpecialization(id));
    }

    @PostMapping("/{specializationId}/doctor/{doctorId}")
    public ResponseEntity<String> assignDoctor(@PathVariable UUID specializationId, @PathVariable UUID doctorId) {
        return ResponseEntity.ok(specializationService.assignDoctor(specializationId, doctorId));
    }

    @DeleteMapping("/{specializationId}/doctor/{doctorId}")
    public ResponseEntity<String> removeDoctor(@PathVariable UUID specializationId, @PathVariable UUID doctorId) {
        return ResponseEntity.ok(specializationService.removeDoctor(specializationId, doctorId));
    }
}
