package rw.auca.terra_trust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.terra_trust.domain.LandOffice;
import java.util.UUID;


public interface LandOfficeRepository extends JpaRepository<LandOffice, UUID> {

}