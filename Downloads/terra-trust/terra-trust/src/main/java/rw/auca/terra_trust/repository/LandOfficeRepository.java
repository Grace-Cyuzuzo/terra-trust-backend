package rw.auca.terra_trust.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.terra_trust.domain.LandOffice;

public interface LandOfficeRepository extends JpaRepository<LandOffice, UUID> {
    
    @Query("SELECT l FROM LandOffice l WHERE NOT EXISTS (SELECT o FROM LandOfficer o WHERE o.landOffice = l) ORDER BY l.officeNumber")
    List<LandOffice> findFreeOffices();
    
    Optional<LandOffice> findByOfficeNumber(int officeNumber);
}