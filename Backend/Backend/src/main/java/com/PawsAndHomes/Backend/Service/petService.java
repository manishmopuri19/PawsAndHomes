package com.PawsAndHomes.Backend.Service;

import java.util.List;

import com.PawsAndHomes.Backend.Dto.requsetDto.createPetRequestDto;
import com.PawsAndHomes.Backend.Dto.responseDto.petResponseDto;

public interface petService {
    petResponseDto createPet(createPetRequestDto dto, Long userId, String role);
    List<petResponseDto> getMyPets(Long userId, String role);
}
