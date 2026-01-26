package com.PawsAndHomes.Backend.Dto.requsetDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class adopterRegitrationDto {

    @NotBlank(message = "Name is required")
    private String Name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
	private String email;

    @NotBlank(message = "mobile is required")
	private String mobile;
	
    @NotBlank(message = "password is required")
    @Size(min=6,message = "password must be at least 6 characters")
	private String password;

public String getName() {
	return Name;
}
public void setName(String name) {
	this.Name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public  String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

}
