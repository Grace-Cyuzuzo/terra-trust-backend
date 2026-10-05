package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.repository.OfficeRepository;

@Service 
public class OfficeService {
    

     @Autowired 
   private OfficeRepository officeRepo;

   public String saveOffice(Office office){
      //before check if we don't have office with same office number.
      Optional<Office> existingOffice = officeRepo.findByOfficeNumber(office.getOfficeNumber());
      if (existingOffice.isPresent()) {
         return "An office with number " + office.getOfficeNumber() + " already exists.";
      }

      officeRepo.save(office);
      return "saved successfully";
   }

   public List<Object[]> getBusiestOffice() {
       return officeRepo.findBusiestOffice(PageRequest.of(0, 1));
   }

   
   

   public String deleteOneOffice(int officeNumber){
    Optional<Office> getOneOffice = officeRepo.findByOfficeNumber(officeNumber);

    if (getOneOffice.isPresent()) {
        officeRepo.deleteById(getOneOffice.get().getId());
        return "Office with number " + officeNumber + " deleted successfully";
    } else {
        return "we don't have office with that office number " + officeNumber;
    }
}

   public Office getOfficeByNumber(int officeNumber) {
    Optional<Office> office = officeRepo.findByOfficeNumber(officeNumber);
    if (office.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No office with number " + officeNumber);
    }
    return office.get();
}

public List<Office> getAllOffices() {
    return officeRepo.findAll();
}

public String updateOffice(int officeNumber, Office updatedOffice) {
    Optional<Office> existing = officeRepo.findByOfficeNumber(officeNumber);
    if (existing.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No office with number " + officeNumber);
    }

    Office office = existing.get();
    office.setName(updatedOffice.getName());
    office.setOfficeNumber(updatedOffice.getOfficeNumber());
    officeRepo.save(office);   // save() updates because 'office' already has an id
    return "updated successfully";
}


}

