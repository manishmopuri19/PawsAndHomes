package com.PawsAndHomes.Backend.Dto.requsetDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ngoRegistartionDto {

    @NotBlank(message = "Orgname is required")
    String Orgname;

    @NotBlank(message = "email is required")
    @Email(message = "invaild email")
    String email;

    @NotBlank(message = "mobile number is required")
    String mobile;

    @NotBlank(message = "location is required")
    String location;

    @NotBlank(message = "password is required")
    String password;

    public String getOrgname(){
        return Orgname;
    }

    public void setOrgname(String orgname) {
		Orgname = orgname;
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

	 public String getLocation() {
		return location;
	}

	 public void setLocation(String location) {
		this.location = location;
	}

	 public String getPassword() {
		return password;
	}

	 public void setPassword(String password) {
		this.password = password;
	}
}
