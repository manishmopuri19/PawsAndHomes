package com.PawsAndHomes.Backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PawsAndHomes.Backend.Dto.requsetDto.adopterLoginDto;
import com.PawsAndHomes.Backend.Dto.requsetDto.adopterRegitrationDto;
import com.PawsAndHomes.Backend.Dto.requsetDto.ngoLoginDto;
import com.PawsAndHomes.Backend.Dto.requsetDto.ngoRegistartionDto;
import com.PawsAndHomes.Backend.Dto.responseDto.ApiResponse;
import com.PawsAndHomes.Backend.Models.Adopter;
import com.PawsAndHomes.Backend.Models.Ngo;
import com.PawsAndHomes.Backend.Service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")

public class AuthController {

	@Autowired
	private AuthService authService;
	
	
	//Registration
	@PostMapping("/Adopter/register")
	public ResponseEntity<ApiResponse> registerAdopter(@Valid  @RequestBody adopterRegitrationDto dto){
		Adopter created=authService.registerAdopter(dto);
		
		return ResponseEntity.status(201).body(new ApiResponse("Adopter registered with id "+created.getId(), true));
	}
	
	@PostMapping("/Ngo/register")
	public ResponseEntity<ApiResponse> registerNgo(@Valid @RequestBody ngoRegistartionDto dto) {
		Ngo created=authService.registerNgo(dto);

		return ResponseEntity.status(201).body(new ApiResponse("Ngo registered with id "+created.getId(),true));
	}
	
	//Logins
	
	@PostMapping("/Adopter/Login")
	public ResponseEntity<ApiResponse> loginAdopter(@Valid @RequestBody adopterLoginDto dto){
		boolean ok=authService.LoginAdopter(dto);
		if(ok){
			return ResponseEntity.ok(new ApiResponse("Login successful",true));
		}

		return ResponseEntity.status(401).body(new ApiResponse("invalid credentials",false));

	}
	
	@PostMapping("/Ngo/Login")
	public ResponseEntity<ApiResponse> loginNgo(@Valid @RequestBody ngoLoginDto dto) {
		
		boolean ok=authService.LoginNgo(dto);
		
		if(ok) return ResponseEntity.ok(new ApiResponse("Login successful",true));

		return ResponseEntity.status(401).body(new ApiResponse("invalid credentials",false));
	}
	
}
