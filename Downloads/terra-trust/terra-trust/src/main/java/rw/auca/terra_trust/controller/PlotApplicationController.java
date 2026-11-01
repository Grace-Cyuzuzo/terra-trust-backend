package rw.auca.terra_trust.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.terra_trust.domain.PlotApplication;
import rw.auca.terra_trust.domain.PlotApplicationStatus;
import rw.auca.terra_trust.service.PlotApplicationService;

@RestController
@RequestMapping("/api/plot-applications")
public class PlotApplicationController {
    private final PlotApplicationService service;
    private final rw.auca.terra_trust.service.PlotPaymentService paymentService;

    public PlotApplicationController(PlotApplicationService service, rw.auca.terra_trust.service.PlotPaymentService paymentService) {
        this.service = service;
        this.paymentService = paymentService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PlotApplication app){
        String result = service.saveSafe(app);

        if (result.equals("PAST_DATE_ERROR")) {
            return new ResponseEntity<> ("Plot application date cannot be in the past", HttpStatus.BAD_REQUEST);

        }

        if(result.equals("OFFICER_BUSY_ERROR")) {
            return new ResponseEntity<> ("Land officer already reviews an application on that date", HttpStatus.CONFLICT);
        }

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
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody PlotApplication newApp) {
        Optional<PlotApplication> existing = service.findById(id);
        if (existing.isPresent()) {
            PlotApplication oldApp = existing.get();
            newApp.setId(id);
            newApp.setStatus(oldApp.getStatus());
            return new ResponseEntity<>(service.save(newApp), HttpStatus.OK);
        }
        return new ResponseEntity<>("The plot application with that id does not exist", HttpStatus.NOT_FOUND);
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<PlotApplication> existing = service.findById(id);
        if (existing.isPresent()) {
            if(paymentService.existsByPlotApplication(existing.get())){
                return new ResponseEntity<>("That plot aaplication has plot payments, delete them first", HttpStatus.CONFLICT);
            }
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
    
    @PatchMapping("/{id}/status")
    public  ResponseEntity<?> changeStatus(@PathVariable UUID id, @RequestParam  PlotApplicationStatus status) {
        Optional<PlotApplication> opt = service.findById(id);
        if(opt.isEmpty()) {
            return new ResponseEntity<>("The plot application with that id does not exist", HttpStatus.NOT_FOUND);

        }
        PlotApplication app = opt.get();
        PlotApplicationStatus current = app.getStatus();

        boolean allowed = false;
        if(current == PlotApplicationStatus.SUBMITTED && (status == PlotApplicationStatus.APPROVED || status == PlotApplicationStatus.REJECTED)) allowed = true;

        if(!allowed) {
            return new ResponseEntity<>("Cannot change status from " + current + " to " + status, HttpStatus.BAD_REQUEST);

        }

        app.setStatus(status);
        service.save(app);

        return new ResponseEntity<>("Plot application status updated successfully", HttpStatus.OK);
    }
    @GetMapping ("/stats/by-status")
    public List<Object[]> statsByStatus() {
        return service.countApplicationsByStatus();
    }
    @PatchMapping("/cancel-day")
    public ResponseEntity<?> cancelDay(@RequestParam UUID landOfficerId,  @RequestParam String date){
        int count = service.cancelDay(landOfficerId, LocalDate.parse(date));
        return new ResponseEntity<>(count + " plot applications cancelled", HttpStatus.OK);
    }
}  

