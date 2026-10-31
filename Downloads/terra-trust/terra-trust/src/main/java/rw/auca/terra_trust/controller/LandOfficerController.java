package rw.auca.terra_trust.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.auca.terra_trust.domain.LandOfficer;
import rw.auca.terra_trust.domain.LandOffice;
import rw.auca.terra_trust.domain.Sector;
import rw.auca.terra_trust.service.LandOfficerService;
import rw.auca.terra_trust.service.LandOfficeService;
import rw.auca.terra_trust.service.SectorService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/land-officers")
public class LandOfficerController {
    private final LandOfficerService service;
    private final SectorService sectorService;
    private final LandOfficeService officeService;

    public LandOfficerController(LandOfficerService service, SectorService sectorService, LandOfficeService officeService) {
        this.service = service;
        this.sectorService = sectorService;
        this.officeService = officeService;

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
    
    @PutMapping("/{landOfficerId}/sectors/{sectorId}")
    public ResponseEntity<?> addSector(@PathVariable UUID landOfficerId, @PathVariable UUID sectorId) {
        Optional<LandOfficer> optOfficer = service.findById(landOfficerId);
        if(optOfficer.isEmpty()) return new ResponseEntity<>("The Land officer with that id does not exist", HttpStatus.NOT_FOUND);

        Optional<Sector> optSector = sectorService.findById(sectorId);
        if (optSector.isEmpty()) return new ResponseEntity<>("That sector does not exist", HttpStatus.NOT_FOUND);

        LandOfficer officer = optOfficer.get();
        Sector sector = optSector.get();

        if(officer.getSectors().contains(sector)) {
            return new ResponseEntity<>("The land officer already has that sector", HttpStatus.CONFLICT);
           }
        
        officer.getSectors().add(sector);
        service.save(officer);
        return new ResponseEntity<>("Sector added to the land officer", HttpStatus.OK);
    }

    @PatchMapping("/{landOfficerId}/land-office")
    public ResponseEntity<?> changeOffice(@PathVariable UUID landOfficerId, @RequestParam int officeNumber){
        Optional<LandOfficer> optOfficer = service.findById(landOfficerId);
        if(optOfficer.isEmpty()) return new ResponseEntity<>("The land officer with that id does not exist", HttpStatus.NOT_FOUND);
        Optional<LandOffice> optOffice = officeService.findByOfficeNumber(officeNumber);
        if(optOffice.isEmpty()) return new ResponseEntity<>("The land office with that officeNumber does not exist", HttpStatus.NOT_FOUND);

        LandOffice newOffice = optOffice.get();
        LandOfficer officer = optOfficer.get();

        if(officer.getLandOffice() != null && !officer.getLandOffice().getId().equals(newOffice.getId()) && service.existsByLandOffice(newOffice)){
           return new ResponseEntity<>("That land office is taken by another land officer", HttpStatus.CONFLICT); 
        }
        officer.setLandOffice(newOffice);
        service.save(officer);

        return new ResponseEntity<>("Land office changed successfully",HttpStatus.OK);
    }
}
