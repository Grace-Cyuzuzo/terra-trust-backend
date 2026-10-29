package rw.auca.terra_trust.service;

import org.springframework.stereotype.Service;
import rw.auca.terra_trust.domain.Buyer;
import rw.auca.terra_trust.repository.BuyerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BuyerService {
    private final BuyerRepository repository;

    public BuyerService(BuyerRepository repository) {
        this.repository = repository;
    }

    public Buyer save(Buyer buyer) { 
        return repository.save(buyer); 
    }
    public List<Buyer> findAll() { 
        return repository.findAll(); 
    }
    public Optional<Buyer> findById(UUID id) { 
        return repository.findById(id); 
    }
    public Buyer update(UUID id, Buyer buyer) { buyer.setId(id); 
        return repository.save(buyer); 
    }
    public void delete(UUID id) { 
        repository.deleteById(id); 
    }
    public List<Buyer> findByLastname(String lastname) {
        return repository.findByLastnameIgnoreCaseOrderByFirstnameAsc(lastname); 
        
    }
}
