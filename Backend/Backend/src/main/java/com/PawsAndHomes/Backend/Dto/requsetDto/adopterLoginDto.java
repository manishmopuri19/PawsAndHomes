package com.PawsAndHomes.Backend.Dto.requsetDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class adopterLoginDto {
    
    @Email(message = "Invalid email")
    @NotBlank(message = "Email required")
    private String email;

    @NotBlank(message = "Password required")
    private String password;

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

     public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    
}
