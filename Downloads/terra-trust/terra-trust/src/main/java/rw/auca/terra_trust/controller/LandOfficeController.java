package rw.auca.terra_trust.controller;


import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.terra_trust.service.*;
import rw.auca.terra_trust.domain.*;

@RestController
@RequestMapping("/api/land-offices")
public class LandOfficeController {
    private final LandOfficeService service;

    public LandOfficeController(LandOfficeService service) {
        this.service =  service;
    }

    @PostMapping ("/save")
    public ResponseEntity<?> save(@RequestBody LandOffice office) {
        service.save(office);
        return new ResponseEntity<>("Land office saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<LandOffice> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<LandOffice> office = service.findById(id);
        return office.<ResponseEntity<?>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("The land office with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody LandOffice office) {
        Optional<LandOffice> existing = service.findById(id);
        if (existing.isPresent()) return new ResponseEntity<>(service.update(id, office), HttpStatus.OK);
        return new ResponseEntity<>("The land office with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<LandOffice> existing = service.findById(id);
        if (existing.isPresent()) {
            service.delete(id);
            return new ResponseEntity<>("Land office deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("The land office with that id does not exist", HttpStatus.NOT_FOUND);
    }
}

