package rw.auca.terra_trust.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.terra_trust.domain.LandOfficer;
import rw.auca.terra_trust.domain.PlotApplication;
import rw.auca.terra_trust.domain.PlotApplicationStatus;

public interface PlotApplicationRepository extends JpaRepository<PlotApplication, UUID> {
    List<PlotApplication> findByStatusOrderByApplicationDateAsc(PlotApplicationStatus status);
    List<PlotApplication> findByApplicationDateBetweenOrderByApplicationDateAsc(LocalDate start, LocalDate end);
    boolean existsByLandOfficerAndApplicationDateAndStatusNot(LandOfficer landOfficer, LocalDate date, PlotApplicationStatus status);
}
