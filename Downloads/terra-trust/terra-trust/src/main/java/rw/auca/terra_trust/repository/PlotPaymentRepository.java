package rw.auca.terra_trust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.auca.terra_trust.domain.PlotApplication;
import rw.auca.terra_trust.domain.PlotPayment;
import java.util.List;
import java.util.UUID;

public interface PlotPaymentRepository extends JpaRepository<PlotPayment, UUID>{
    boolean existsByPlotApplication(PlotApplication app);

    @Query("SELECT p FROM PlotPayment p WHERE p.plotApplication.buyer.nationalId = :nationalId ORDER BY p.paymentDate DESC")
    List<PlotPayment> findByBuyerNationalId(@Param("nationalId") String nationalId);
    
}
