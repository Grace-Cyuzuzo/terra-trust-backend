package rw.auca.terra_trust.service;

import org.springframework.stereotype.Service;
import rw.auca.terra_trust.domain.LandOffice;
import rw.auca.terra_trust.repository.LandOfficeRepository;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class LandOfficeService {

    private final LandOfficeRepository  repository;

    public LandOfficeService(LandOfficeRepository repository) {
        this.repository = repository;

    }

    public LandOffice save(LandOffice  office) {
        return repository.save(office);
    }
    public List<LandOffice> findAll() {
        return repository.findAll();

    }

    public Optional<LandOffice> findById(UUID id) {
        return repository.findById(id);
    }

    public LandOffice update(UUID id, LandOffice office) {
        office.setId(id);
        return repository.save(office);
    }
    public void delete(UUID id) {
        repository.deleteById(id);
    }
    public List<LandOffice> findFreeOffices() {
        return repository.findFreeOffices();
    }

}