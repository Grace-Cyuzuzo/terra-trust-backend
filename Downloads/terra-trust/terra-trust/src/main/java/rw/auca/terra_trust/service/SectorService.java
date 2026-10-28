package rw.auca.terra_trust.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.auca.terra_trust.domain.Sector;
import rw.auca.terra_trust.repository.SectorRepository;

@Service
public class SectorService {
    private final SectorRepository repository;

    public SectorService(SectorRepository repository) {
        this.repository = repository;
    }

    public Sector save(Sector sector) { return repository.save(sector); }
    public List<Sector> findAll() { return repository.findAll(); }
    public Optional<Sector> findById(UUID id) { return repository.findById(id); }
    public Sector update(UUID id, Sector sector) { sector.setId(id); return repository.save(sector); }
    public void delete(UUID id) { repository.deleteById(id); }
}