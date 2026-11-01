 package rw.auca.terra_trust.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.terra_trust.domain.LandOfficer;
import rw.auca.terra_trust.domain.LandOffice;
import java.util.List;
import org .springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

public interface LandOfficerRepository extends JpaRepository<LandOfficer, UUID> {
    @Query("SELECT DISTINCT o FROM LandOfficer o JOIN o.sectors s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name,'%'))")
    List<LandOfficer> findBySectorName(@Param("name") String name);
    boolean existsByLandOffice(LandOffice landOffice);

}
