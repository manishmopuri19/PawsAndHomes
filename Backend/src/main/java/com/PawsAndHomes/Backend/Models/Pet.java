package com.PawsAndHomes.Backend.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets")
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String breed;
    private Integer age;
    private String gender;
    private String size;
    private String imageUrl;
    
    private String status = "AVAILABLE"; // AVAILABLE, ADOPTED, PENDING
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ngo_id")
    private Ngo ngo;

    public Long getId(){
        return id; 
    }
    public void setId(Long id){ this.id = id; }
    
    public String getName(){ 
        return name; 
    }
    public void setName(String name){ 
        this.name = name; 
    }
    
    public String getBreed(){ 
        return breed; 
    }
    public void setBreed(String breed){ 
        this.breed = breed; 
    }
    
    public Integer getAge(){ 
        return age; 
    }
    public void setAge(Integer age){ 
        this.age = age; 
    }
    
    public String getGender(){ 
        return gender; 
    }
    public void setGender(String gender){ 
        this.gender = gender; 
    }
    
    public String getSize(){ 
        return size; 
    }
    public void setSize(String size){ 
        this.size = size; 
    }
    
    public String getImageUrl(){ 
        return imageUrl; 
    }
    public void setImageUrl(String imageUrl){ 
        this.imageUrl = imageUrl; 
    }
    
    public String getStatus(){ 
        return status; 
    }
    public void setStatus(String status){ 
        this.status = status; 
    }
    
    public Adopter getAdopter(){ 
        return adopter; 
    }
    public void setAdopter(Adopter adopter){ 
        this.adopter = adopter; 
    }
    
    public Ngo getNgo(){ 
        return ngo; 
    }
    public void setNgo(Ngo ngo){ 
        this.ngo = ngo; 
    }

   

}
