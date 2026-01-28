package com.PawsAndHomes.Backend.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ngos")
@Data
@NoArgsConstructor
public class Ngo extends User {
    private String orgName;
    private String location;
    
    @OneToMany(mappedBy = "ngo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pet> managedPets = new ArrayList<>();
    
    @OneToMany(mappedBy = "ngo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents = new ArrayList<>();
	//  public Ngo() {
    //     super();
    // }

    // Getters and Setters
    public String getOrgName(){ 
        return orgName; 
    }
    public void setOrgName(String orgName){ 
        this.orgName = orgName; 
    }
    
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){ 
        this.location = location; 
    }
    
    public List<Pet> getManagedPets(){ 
        return managedPets; 
    }
    public void setManagedPets(List<Pet> managedPets){ 
        this.managedPets = managedPets; 
    }
    
    public List<Document> getDocuments(){ 
        return documents; 
    }
    public void setDocuments(List<Document> documents){ 
        this.documents = documents; 
    }

   

}
