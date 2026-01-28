package com.PawsAndHomes.Backend.Models;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String name;
    
    private String mobile;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private boolean enabled = true;
    
    private LocalDateTime createdAt = LocalDateTime.now();

    public User() {}

    // Getters and Setters
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id; 
    }
    
    public String getEmail(){ 
        return email; 
    }
    public void setEmail(String email){ 
        this.email = email; 
    }
    
    public String getName(){ 
        return name; 
    }
    public void setName(String name){ 
        this.name = name; 
    }
    
    public String getMobile(){ 
        return mobile; 
    }
    public void setMobile(String mobile){
        this.mobile = mobile; 
    }
    
    public String getPassword(){ 
        return password; 
    }
    public void setPassword(String password){ 
        this.password = password; 
    }
    
    public Role getRole(){ 
        return role; 
    }
    public void setRole(Role role){ 
        this.role = role; 
    }
    
    public boolean isEnabled(){ 
        return enabled; 
    }
    public void setEnabled(boolean enabled){ 
        this.enabled = enabled; 
    }
    
    public LocalDateTime getCreatedAt(){ 
        return createdAt; 
    }
    public void setCreatedAt(LocalDateTime createdAt){ 
        this.createdAt = createdAt; 
    }

}
