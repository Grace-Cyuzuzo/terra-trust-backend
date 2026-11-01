package rw.auca.terra_trust.service;

import org.springframework.stereotype.Service;
import rw.auca.terra_trust.domain.PlotApplication;
import rw.auca.terra_trust.domain.PlotPayment;
import rw.auca.terra_trust.repository.PlotApplicationRepository;
import rw.auca.terra_trust.repository.PlotPaymentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class PlotPaymentService {
    private final PlotPaymentRepository repository;
    private final PlotApplicationRepository appRepository;

    public PlotPaymentService(PlotPaymentRepository repository, PlotApplicationRepository appRepository){
        this.repository = repository;
        this.appRepository =  appRepository;
    }

    public String savePayment(UUID appId, PlotPayment payment){
        Optional<PlotApplication> optApp = appRepository.findById(appId);
        if(optApp.isEmpty()) return "APP_NOT_FOUND";

        PlotApplication app = optApp.get();
        if(app.getStatus( ) != rw.auca.terra_trust.domain.PlotApplicationStatus.APPROVED)
        {
            return "NOT_APPROVED";
        } 
        payment.setPaymentDate(LocalDate.now());
        payment.setPlotApplication(app);
        repository.save(payment);
        return "SUCCESS";  
    }

    public List<PlotPayment> findByBuyerNationalId(String nationalId) {
        return repository.findByBuyerNationalId(nationalId);
    }

    public boolean existsByPlotApplication(PlotApplication app){
        return repository.existsByPlotApplication(app);
    }
    public Optional<PlotPayment> findById(UUID id) {
        return repository.findById(id);
    }
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
