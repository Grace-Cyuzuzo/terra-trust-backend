package rw.auca.terra_trust.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.terra_trust.domain.Sector;
import rw.auca.terra_trust.service.SectorService;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {
    private final SectorService service;

    public SectorController(SectorService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Sector sector) {
        service.save(sector);
        return new ResponseEntity<>("Sector saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Sector> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Sector> sector = service.findById(id);
        return sector.<ResponseEntity<?>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("The sector with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Sector sector) {
        Optional<Sector> existing = service.findById(id);
        if (existing.isPresent()) return new ResponseEntity<>(service.update(id, sector), HttpStatus.OK);
        return new ResponseEntity<>("The sector with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<Sector> existing = service.findById(id);
        if (existing.isPresent()) {
            service.delete(id);
            return new ResponseEntity<>("Sector deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("The sector with that id does not exist", HttpStatus.NOT_FOUND);
    }
}
