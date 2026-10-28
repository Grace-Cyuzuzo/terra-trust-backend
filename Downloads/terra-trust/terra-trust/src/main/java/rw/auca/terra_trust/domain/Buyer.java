package rw.auca.terra_trust.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "buyer")
public class Buyer {

    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String nationalId;

    private String email;
    private String phone;

    //Getters and Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID ID){
        this.id = 
        id;

    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName( String lastName) {
        this.lastName =  lastName;
    }
    
    public String getNationalId() {
        return nationalId;
    }
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}