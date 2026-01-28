package com.PawsAndHomes.Backend.Dto.Auth;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String userId;
    private String email;
    private String name;
    private String role;

     public AuthResponseDTO() {}

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

}