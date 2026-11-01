package rw.auca.terra_trust.service;

import org.springframework.stereotype.Service;
import rw.auca.terra_trust.domain.PlotApplication;
import rw.auca.terra_trust.repository.PlotApplicationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;
import rw.auca.terra_trust.domain.PlotApplicationStatus;
import org.springframework.transaction.annotation.Transactional;

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
    public String saveSafe(PlotApplication app) {
        //Rule 1: Date cannot be in the past
        if (app.getApplicationDate().isBefore(LocalDate.now())) {
            return "PAST_DATE_ERROR";
        }
        //Rule 2: Officer already has an app on this date that is not rejected
        if(app.getLandOfficer() != null && repository.existsByLandOfficerAndApplicationDateAndStatusNot(app.getLandOfficer(), app.getApplicationDate(), PlotApplicationStatus.REJECTED)){
            return "OFFICER_BUSY_ERROR";
        }

        //Rule 3: Always force status to SUBMITTED
        app.setStatus(PlotApplicationStatus.SUBMITTED);

        repository.save(app);
        return "SUCCESS";
    }
    public List<Object[]> countApplicationsByStatus() {
        return repository.countApplicationsByStatus();
    }

    @Transactional
    public int cancelDay(UUID officerId, LocalDate date) {
        return repository.cancelApplications(officerId, date);
    }
}
