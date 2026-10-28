package rw.auca.terra_trust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.terra_trust.domain.Sector;
import java.util.UUID;

public interface SectorRepository extends JpaRepository<Sector, UUID> {
}