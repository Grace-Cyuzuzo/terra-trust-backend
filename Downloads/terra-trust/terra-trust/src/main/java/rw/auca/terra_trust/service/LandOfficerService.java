package rw.auca.terra_trust.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.auca.terra_trust.domain.LandOfficer;
import rw.auca.terra_trust.repository.LandOfficerRepository;

@Service
public class LandOfficerService {
    private final LandOfficerRepository repository;

    public LandOfficerService(LandOfficerRepository repository) {
        this.repository = repository;
    }

    public LandOfficer save(LandOfficer officer) { 
        return repository.save(officer); 
    }
    public List<LandOfficer> findAll() { 
        return repository.findAll(); 
    }
    public Optional<LandOfficer> findById(UUID id) { 
        return repository.findById(id); 
    }
    public LandOfficer update(UUID id, LandOfficer officer) {  officer.setId(id); 
        return repository.save(officer); 
    }
    public void delete(UUID id) { 
        repository.deleteById(id); 
    }
}
