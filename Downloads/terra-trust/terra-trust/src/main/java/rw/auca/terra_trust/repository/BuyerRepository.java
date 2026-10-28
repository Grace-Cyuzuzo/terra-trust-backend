package rw.auca.terra_trust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.terra_trust.domain.Buyer;
import java.util.UUID;

public interface BuyerRepository extends JpaRepository<Buyer, UUID> {
}
