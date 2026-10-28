package rw.auca.terra_trust.domain;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDate;

@Entity 
@Table(name = "plot_application")

public class PlotApplication {
   
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    private PlotApplicationStatus status;

    private String  plotNumber;
    private double offeredPrice;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne 
    @JoinColumn( name = "land_officer_id")
    private LandOfficer landOfficer;

    //Getters and Setters
    public UUID getId() {
        return id;

    }
    public void setId(UUID id) {
        this.id = id;
    }
    public LocalDate getApplicationDate() {
        return applicationDate;
    }
    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }
    
    public PlotApplicationStatus getStatus() {
        return status;
    }
    public void setStatus(PlotApplicationStatus status) {
        this.status = status;
    }
    
    public String getPlotNumber() {
        return plotNumber;
    }
    public void setPlotNumber(String plotNumber) {
        this.plotNumber = plotNumber;
    }
    
    public double getOfferedPrice() {
        return offeredPrice;
    }
    public void setOfferedPrice(double offeredPrice) {
        this.offeredPrice = offeredPrice;
    }
    
    public Buyer getBuyer() {
        return buyer;
    }
    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }
    
    public LandOfficer getLandOfficer() {
        return landOfficer;
    }
    public void setLandOfficer(LandOfficer landOfficer) {
        this.landOfficer = landOfficer;
    }
}