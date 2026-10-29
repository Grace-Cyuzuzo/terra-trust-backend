package rw.auca.terra_trust.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.auca.terra_trust.domain.LandOfficer;
import rw.auca.terra_trust.service.LandOfficerService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/land-officers")
public class LandOfficerController {
    private final LandOfficerService service;

    public LandOfficerController(LandOfficerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody LandOfficer officer) {
        service.save(officer);
        return new ResponseEntity<>("Land officer saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<LandOfficer> getAll() { 
        return service.findAll(); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<LandOfficer> officer = service.findById(id);
        return officer.<ResponseEntity<?>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("The land officer with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody LandOfficer officer) {
        Optional<LandOfficer> existing = service.findById(id);
        if (existing.isPresent()) return new ResponseEntity<>(service.update(id, officer), HttpStatus.OK);
        return new ResponseEntity<>("The land officer with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<LandOfficer> existing = service.findById(id);
        if (existing.isPresent()) {
            service.delete(id);
            return new ResponseEntity<>("Land officer deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("The land officer with that id does not exist", HttpStatus.NOT_FOUND);
    }
    @GetMapping ("/by-sector")
    public List<LandOfficer> bySector(@RequestParam String name) {
        return service.findBySectorName(name);
    }
    
}
