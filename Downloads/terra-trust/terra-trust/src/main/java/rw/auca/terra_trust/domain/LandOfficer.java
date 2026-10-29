package rw.auca.terra_trust.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table; 
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "land_officer")

public class LandOfficer {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastname;
    private String email;

    @ManyToOne
    @JoinColumn(name = "land_office_id")
    private LandOffice landOffice;

    @ManyToMany
    @JoinTable(name = "land_officer_sector")
    private List<Sector> sectors;

    //Getters and Setters
    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getLastname(){
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LandOffice getLandOffice() {
        return landOffice;
    }

    public void setLandOffice( LandOffice landOffice) {
        this.landOffice = landOffice;
    }

    public List<Sector> getSectors() {
        return sectors;
    }
    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }

}
