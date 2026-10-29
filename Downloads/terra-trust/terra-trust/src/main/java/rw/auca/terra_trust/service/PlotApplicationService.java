package rw.auca.terra_trust.service;

import org.springframework.stereotype.Service;
import rw.auca.terra_trust.domain.PlotApplication;
import rw.auca.terra_trust.repository.PlotApplicationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;
import rw.auca.terra_trust.domain.PlotApplicationStatus;

@Service
public class PlotApplicationService {
    private final PlotApplicationRepository repository;

    public PlotApplicationService(PlotApplicationRepository repository) {
        this.repository = repository;
    }

    public PlotApplication save(PlotApplication app) { 
        return repository.save(app); 
    }
    public List<PlotApplication> findAll() { 
        return repository.findAll(); 
    }
    public Optional<PlotApplication> findById(UUID id) { 
        return repository.findById(id); 
    }
    public PlotApplication update(UUID id, PlotApplication app) { app.setId(id); 
        return repository.save(app); 
    }
    public void delete(UUID id) { 
        repository.deleteById(id); 
    }

    public List<PlotApplication> findByStatus (PlotApplicationStatus status) {
        return repository.findByStatusOrderByApplicationDateAsc(status);
    }

    public List<PlotApplication> findBetween(LocalDate start, LocalDate  end) {
        return repository.findByApplicationDateBetweenOrderByApplicationDateAsc(start, end);
    }
}
