package rw.auca.terra_trust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.terra_trust.domain.PlotApplication;
import java.util.UUID;
import java.util.List;
import rw.auca.terra_trust.domain.PlotApplicationStatus;
import java.time.LocalDate;

public interface PlotApplicationRepository extends JpaRepository<PlotApplication, UUID> {
    List<PlotApplication> findByStatusOrderByApplicationDateAsc(PlotApplicationStatus status);
    List<PlotApplication> findByApplicationDateBetweenOrderByApplicationDateAsc(LocalDate start, LocalDate end);
}
