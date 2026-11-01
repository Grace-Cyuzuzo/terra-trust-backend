package rw.auca.terra_trust.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity 
@Table (name = "plot_payment")
public class PlotPayment {
  @Id 
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  
  private String paymentMethod;
  private String reference;
  private int amount;
  private LocalDate paymentDate;

  @ManyToOne
  @JoinColumn(name = "plot_application_id")
  private PlotApplication plotApplication;

  //Getters and Setters
  public UUID getId(){
    return id;
  }
  public void setId(UUID id){
    this.id = id;
  }
  public String getPaymentMethod(){
    return paymentMethod;

  }
  public void stePayMethos(String paymentMethod){
    this.paymentMethod =  paymentMethod;
  }
  public String getReference(){
    return reference;
  }
  public void setReference(String reference){
    this.reference =  reference;
  }
  public int getAmount(){
    return amount;
  }
  public void setAmount(int amount){
    this.amount =  amount;
  }
  public LocalDate getPaymentDate(){
    return paymentDate;
  }

  public void setPaymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate; 
  }
  public PlotApplication getPlotApplication(){
    return plotApplication;
  }
  public void setPlotApplication(PlotApplication plotApplication){
    this.plotApplication = plotApplication;
  }
}
