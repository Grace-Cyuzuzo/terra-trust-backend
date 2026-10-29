package rw.auca.terra_trust.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.terra_trust.domain.LandOffice;
import java.util.List;
import org.springframework.data.jpa.repository.Query;


public interface LandOfficeRepository extends JpaRepository<LandOffice, UUID> {
    @Query("SELECT 1 FROM LandOffice 1 WHERE NOT EXISTS (SELECT o FROM LandOfficer o WHERE o.landOffice = 1) ORDER BY 1.officeNumberber")
    List<LandOffice> findFreeOffices();
}