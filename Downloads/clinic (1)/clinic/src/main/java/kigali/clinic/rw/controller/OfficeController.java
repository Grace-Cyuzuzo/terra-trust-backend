package kigali.clinic.rw.controller;

import java.util.List;

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

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.service.OfficeService;


@RestController 
@RequestMapping (value = "/api/offices")
public class OfficeController {
  
    @Autowired 
    private OfficeService officeService;

    
     @PostMapping(value="/save", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> saveOffice(@RequestBody Office office){
        
      String returnedMessage =  officeService.saveOffice(office);
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED );
    }

    @GetMapping("/{officeNumber}")
public ResponseEntity<Office> getOffice(@PathVariable int officeNumber) {
    return ResponseEntity.ok(officeService.getOfficeByNumber(officeNumber));
}

@GetMapping
public ResponseEntity<List<Office>> getAllOffices() {
    return ResponseEntity.ok(officeService.getAllOffices());
}

@PutMapping("/{officeNumber}")
public ResponseEntity<String> updateOffice(@PathVariable int officeNumber, @RequestBody Office office) {
    return ResponseEntity.ok(officeService.updateOffice(officeNumber, office));
}

@DeleteMapping("/{officeNumber}")
public ResponseEntity<String> deleteOffice(@PathVariable int officeNumber) {
    return ResponseEntity.ok(officeService.deleteOneOffice(officeNumber));
}
}

