package rw.auca.terra_trust.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "land_office")
public class LandOffice {
    
    @Id 
    @GeneratedValue
    private UUID id;
    
    private int officeNumber; 
    private String name;

    // Getters and Setters
    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public int getOfficeNumber(){
        return officeNumber;
    }

    public void setOfficeNumber(int officeNumber){
        this.officeNumber = officeNumber;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}