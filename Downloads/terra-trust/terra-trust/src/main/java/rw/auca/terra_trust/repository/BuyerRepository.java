package rw.auca.terra_trust.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.terra_trust.domain.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, UUID> {
    List<Buyer> findByLastnameIgnoreCaseOrderByFirstnameAsc(String lastname);
    @Query("SELECT DISTINCT b FROM Buyer b JOIN PlotApplication p.buyer = b WHERE p.landOfficer.id = :landOfficerId ")
    List<Buyer> findBuyersByLandOfficerId(@Param ("landOfficerId") UUID landOfficerId);
}
