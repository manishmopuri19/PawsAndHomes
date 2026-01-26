package com.PawsAndHomes.Backend.Service;

import com.PawsAndHomes.Backend.Dto.requsetDto.adopterLoginDto;
import com.PawsAndHomes.Backend.Dto.requsetDto.adopterRegitrationDto;
import com.PawsAndHomes.Backend.Dto.requsetDto.ngoLoginDto;
import com.PawsAndHomes.Backend.Dto.requsetDto.ngoRegistartionDto;
import com.PawsAndHomes.Backend.Models.Adopter;
import com.PawsAndHomes.Backend.Models.Ngo;

public interface AuthService{
	
Adopter registerAdopter(adopterRegitrationDto dto);

boolean LoginAdopter(adopterLoginDto dto);

Ngo registerNgo(ngoRegistartionDto dto);

boolean LoginNgo(ngoLoginDto dto);
}
