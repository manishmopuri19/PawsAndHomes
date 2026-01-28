package com.PawsAndHomes.Backend.Dto.Auth;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String email;
    private String password;
    private String name;
    private String mobile;
    private String role; // "ADOPTER" or "NGO"
    private String orgName; // for NGO
    private String location; // for NGO

    
    public RegisterRequestDTO() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getOrgName() { return orgName; }
    public void setOrgName(String orgName) { this.orgName = orgName; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

}

