 package rw.auca.terra_trust.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.terra_trust.domain.LandOfficer;

public interface LandOfficerRepository extends JpaRepository<LandOfficer, UUID> {
}
