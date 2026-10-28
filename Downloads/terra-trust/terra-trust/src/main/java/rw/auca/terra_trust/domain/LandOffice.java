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
    private int OfficeNumber;
    private String name;

    //Getters and Setters
    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public int getOfficeNumber(){
        return OfficeNumber;
    }

    public void setOfficeNumber(int OfficeNumber){
        this.OfficeNumber = OfficeNumber;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
