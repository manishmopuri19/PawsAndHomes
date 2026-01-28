package com.PawsAndHomes.Backend.Models;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "adopters")
@NoArgsConstructor
public class Adopter extends User {
    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pet> adoptedPets = new ArrayList<>();
    
    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents = new ArrayList<>();
	// public Adopter() {
    //     super();
    // }

    // Getters and Setters
    public List<Pet> getAdoptedPets() { return adoptedPets; }
    public void setAdoptedPets(List<Pet> adoptedPets) { this.adoptedPets = adoptedPets; }
    
    public List<Document> getDocuments() { return documents; }
    public void setDocuments(List<Document> documents) { this.documents = documents; }

}
