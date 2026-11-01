package rw.auca.terra_trust.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.auca.terra_trust.domain.Buyer;
import rw.auca.terra_trust.service.BuyerService;
import rw.auca.terra_trust.service.LandOfficerService;
import rw.auca.terra_trust.domain.LandOfficer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/buyers")
public class BuyerController {
    private final BuyerService service;
    private final LandOfficerService landOfficerService;

    public BuyerController(BuyerService service, LandOfficerService landOfficerService) {
        this.service = service;
        this.landOfficerService = landOfficerService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Buyer buyer) {
        service.save(buyer);
        return new ResponseEntity<>("Buyer saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Buyer> getAll() { 
        return service.findAll(); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Buyer> buyer = service.findById(id);
        return buyer.<ResponseEntity<?>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("The buyer with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Buyer buyer) {
        Optional<Buyer> existing = service.findById(id);
        if (existing.isPresent()) 
        return new ResponseEntity<>(service.update(id, buyer), HttpStatus.OK);
        return new ResponseEntity<>("The buyer with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<Buyer> existing = service.findById(id);
        if (existing.isPresent()) {
            service.delete(id);
            return new ResponseEntity<>("Buyer deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("The buyer with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-last-name")
    public List<Buyer> byLastName(@RequestParam String lastName) {
        return service.findByLastName(lastName);
    }
    @GetMapping ("/of-land-officer/{landOfficerId}")
    public ResponseEntity<?> getBuyersOfOfficer(@PathVariable  UUID landOfficerId){
        Optional<LandOfficer> officer =  landOfficerService.findById(landOfficerId);
        if (officer.isEmpty()) {
            return new ResponseEntity<>("The land Officer with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(service.findBuyersByLandOfficerId(landOfficerId), HttpStatus.OK);
        
    }
}
