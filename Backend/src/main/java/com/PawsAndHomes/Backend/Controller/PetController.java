package com.PawsAndHomes.Backend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


import com.PawsAndHomes.Backend.Dto.Pet.PetRequestDTO;
import com.PawsAndHomes.Backend.Dto.Pet.PetResponseDTO;
import com.PawsAndHomes.Backend.Service.PetService;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {
    
    private final PetService petService;
    
    @GetMapping("/public")
    public ResponseEntity<List<PetResponseDTO>> getAvailablePets() {
        return ResponseEntity.ok(petService.getAvailablePets());
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADOPTER', 'NGO')")
    public ResponseEntity<List<PetResponseDTO>> getMyPets() {
        return ResponseEntity.ok(petService.getMyPets());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('NGO')")
    public ResponseEntity<PetResponseDTO> createPet(@RequestBody PetRequestDTO request) {
        return ResponseEntity.ok(petService.createPet(request));
    }
}