package com.PawsAndHomes.Backend.Service;
import com.PawsAndHomes.Backend.Dto.Pet.PetRequestDTO;
import com.PawsAndHomes.Backend.Dto.Pet.PetResponseDTO;
import java.util.List;
public interface PetService {
    List<PetResponseDTO> getAvailablePets();
    List<PetResponseDTO> getMyPets();
    PetResponseDTO createPet(PetRequestDTO request);
    PetResponseDTO getPetById(Long id);
    PetResponseDTO updatePetStatus(Long petId, String status);
}