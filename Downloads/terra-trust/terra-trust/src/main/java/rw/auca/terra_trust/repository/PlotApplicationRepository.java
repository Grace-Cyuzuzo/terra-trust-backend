package rw.auca.terra_trust.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import rw.auca.terra_trust.domain.LandOfficer;
import rw.auca.terra_trust.domain.PlotApplication;
import rw.auca.terra_trust.domain.PlotApplicationStatus;

public interface PlotApplicationRepository extends JpaRepository<PlotApplication, UUID> {
    List<PlotApplication> findByStatusOrderByApplicationDateAsc(PlotApplicationStatus status);
    List<PlotApplication> findByApplicationDateBetweenOrderByApplicationDateAsc(LocalDate start, LocalDate end);
    @Query ("SELECT COUNT(p) >0 FROM PlotApplication p WHERE p.landOfficer = :landOfficer AND p.applicationDate = :date AND p.status != :status")
    boolean existsByLandOfficerAndApplicationDateAndStatusNot(
        @Param("landOfficer") LandOfficer landOfficer, 
        @Param("date") LocalDate date,
        @Param("status") PlotApplicationStatus status);
        
    @Query("SELECT p.status, COUNT(p) FROM PlotApplication p GROUP BY p.status")
    List<Object[]> countApplicationsByStatus();
    @Modifying
    @Query ("UPDATE PlotApplication p SET p.status = 'REJECTED' WHERE p.landOfficer.id = :officerId AND p.applicationDate = :date AND p.status != 'APPROVED'")
    int cancelApplications(@Param("officerId") UUID officerId, @Param("date") LocalDate date);
}


