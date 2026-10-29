package rw.auca.terra_trust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.terra_trust.domain.Sector;
import java.util.UUID;
import java.util.List;

public interface SectorRepository extends JpaRepository<Sector, UUID> {
    @Query ("SELECT s FROM Sector s WHERE s.landOfficers IS EMPTY")
    List<Sector> findUnusedSectors();
}