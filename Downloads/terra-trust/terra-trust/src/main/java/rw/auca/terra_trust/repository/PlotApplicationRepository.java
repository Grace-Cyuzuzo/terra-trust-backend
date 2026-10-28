package rw.auca.terra_trust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.terra_trust.domain.PlotApplication;
import java.util.UUID;

public interface PlotApplicationRepository extends JpaRepository<PlotApplication, UUID> {
}
