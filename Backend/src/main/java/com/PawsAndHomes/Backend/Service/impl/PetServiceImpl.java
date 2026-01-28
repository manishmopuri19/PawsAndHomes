package com.PawsAndHomes.Backend.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.PawsAndHomes.Backend.Dto.Pet.PetRequestDTO;
import com.PawsAndHomes.Backend.Dto.Pet.PetResponseDTO;
import com.PawsAndHomes.Backend.Models.Adopter;
import com.PawsAndHomes.Backend.Models.Ngo;
import com.PawsAndHomes.Backend.Models.Pet;
import com.PawsAndHomes.Backend.Repository.AdopterRepository;
import com.PawsAndHomes.Backend.Repository.NgoRepository;
import com.PawsAndHomes.Backend.Repository.PetRepository;
import com.PawsAndHomes.Backend.Service.PetService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class PetServiceImpl implements PetService {
    
    private final PetRepository petRepository;
    private final NgoRepository ngoRepository;
    private final AdopterRepository adopterRepository;

    @Override
    public List<PetResponseDTO> getAvailablePets() {
        return petRepository.findAvailablePets().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetResponseDTO> getMyPets() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Check if user is NGO or Adopter
        Ngo ngo = ngoRepository.findByEmail(email).orElse(null);
        Adopter adopter = adopterRepository.findByEmail(email).orElse(null);
        
        List<Pet> pets;
        if (ngo != null) {
            pets = petRepository.findByNgoId(ngo.getId());
        } else if (adopter != null) {
            pets = petRepository.findByAdopter_Id(adopter.getId());
        } else {
            throw new RuntimeException("User not found");
        }
        
        return pets.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PetResponseDTO createPet(PetRequestDTO request) {
        Ngo ngo = ngoRepository.findById(request.getNgoId())
                .orElseThrow(() -> new RuntimeException("NGO not found"));
        
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setBreed(request.getBreed());
        pet.setAge(request.getAge());
        pet.setGender(request.getGender());
        pet.setSize(request.getSize());
        pet.setNgo(ngo);
        pet.setStatus("AVAILABLE");
        
        Pet savedPet = petRepository.save(pet);
        return convertToResponseDTO(savedPet);
    }

    @Override
    public PetResponseDTO getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        return convertToResponseDTO(pet);
    }

    @Override
    public PetResponseDTO updatePetStatus(Long petId, String status) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        
        pet.setStatus(status);
        Pet updatedPet = petRepository.save(pet);
        return convertToResponseDTO(updatedPet);
    }
    
    private PetResponseDTO convertToResponseDTO(Pet pet) {
        PetResponseDTO dto = new PetResponseDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBreed(pet.getBreed());
        dto.setAge(pet.getAge());
        dto.setGender(pet.getGender());
        dto.setSize(pet.getSize());
        dto.setImageUrl(pet.getImageUrl());
        dto.setStatus(pet.getStatus());
        
        if (pet.getNgo() != null) {
            dto.setNgoName(pet.getNgo().getName());
        }
        if (pet.getAdopter() != null) {
            dto.setAdopterName(pet.getAdopter().getName());
        }
        
        return dto;
    }
}