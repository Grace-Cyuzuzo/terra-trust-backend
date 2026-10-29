package rw.auca.terra_trust.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.auca.terra_trust.domain.PlotApplication;
import rw.auca.terra_trust.service.PlotApplicationService;
import rw.auca.terra_trust.service.PlotApplicationStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/plot-applications")
public class PlotApplicationController {
    private final PlotApplicationService service;

    public PlotApplicationController(PlotApplicationService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PlotApplication app) {
        service.save(app);
        return new ResponseEntity<>("Plot application saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<PlotApplication> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<PlotApplication> app = service.findById(id);
        return app.<ResponseEntity<?>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("The plot application with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody PlotApplication app) {
        Optional<PlotApplication> existing = service.findById(id);
        if (existing.isPresent()) return new ResponseEntity<>(service.update(id, app), HttpStatus.OK);
        return new ResponseEntity<>("The plot application with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<PlotApplication> existing = service.findById(id);
        if (existing.isPresent()) {
            service.delete(id);
            return new ResponseEntity<>("Plot application deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("The plot application with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @GetMapping ("/by-status")
    public List<PlotApplication> byStatus(@RequestParam PlotApplicationStatus status) {
        return service.findByStatus(status);
    }
    @GetMapping("/between")
    public List<PlotApplication> between(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return service.findBetween(startDate, endDate);
    }

}
