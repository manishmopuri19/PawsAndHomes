package com.PawsAndHomes.Backend.Service.impl;

import java.util.List;

import com.PawsAndHomes.Backend.Dto.requsetDto.createPetRequestDto;
import com.PawsAndHomes.Backend.Dto.responseDto.petResponseDto;
import com.PawsAndHomes.Backend.Repository.AdopterRepo;
import com.PawsAndHomes.Backend.Repository.NgoRepo;
import com.PawsAndHomes.Backend.Repository.petRepo;
import com.PawsAndHomes.Backend.Service.petService;

public class PetServiceImpl{
	
	private final petRepo petRepository;
    private final AdopterRepo adopterRepository;
    private final NgoRepo ngoRepository;

    public PetServiceImpl(petRepo petRepository,
                          AdopterRepo adopterRepository,
                          NgoRepo ngoRepository) {
        this.petRepository = petRepository;
        this.adopterRepository = adopterRepository;
        this.ngoRepository = ngoRepository;
    }

	

}
