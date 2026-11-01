package rw.auca.terra_trust.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.auca.terra_trust.domain.PlotPayment;
import rw.auca.terra_trust.service.PlotPaymentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/plot-payments")
public class PlotPaymentContoller{
    private final PlotPaymentService service;

    public PlotPaymentContoller(PlotPaymentService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<?>save(@RequestParam UUID plotApplicationId, @RequestBody PlotPayment payment) {
        String result = service.savePayment(plotApplicationId, payment);

        if(result.equals("APP_NOT_FOUND")) {
            return new ResponseEntity<>("The plot application with that id does not exist",HttpStatus.NOT_FOUND);
        }
        if(result.equals("NOT_APPROVED")) {
            return new ResponseEntity<>("Plot payments are only for APPROVED plot applications", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Plot payment saved successfully", HttpStatus.CREATED);
    }

    @GetMapping ("/buyer/{nationalId}")
    public List<PlotPayment> getByBUyer(@PathVariable String nationalId){
        return service.findByBuyerNationalId(nationalId);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?>delete(@PathVariable UUID id) {
        Optional<PlotPayment> payment =  service.findById(id);
        if(payment.isPresent()){
            service.delete(id);
            return new ResponseEntity<>("Plot payment deleted successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("The plot payment with that id does not exist", HttpStatus.NOT_FOUND);
    }
}