package rw.auca.terra_trust.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sector")

public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @ManyToMany(mappedBy = "sectors")
    @JsonIgnore
    private List<LandOfficer> landOfficers;
    

    //Getters and Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;

    }
    public List<LandOfficer
    > getLandOfficers() {
        return landOfficers;

    }
    public void setLandOfficers(List<LandOfficer> landOfficers) {
        this.landOfficers = landOfficers;
    }
}