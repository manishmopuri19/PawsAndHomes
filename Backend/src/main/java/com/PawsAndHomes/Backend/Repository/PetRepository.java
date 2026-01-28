package com.PawsAndHomes.Backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.PawsAndHomes.Backend.Models.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNgoId(Long ngoId);
    List<Pet> findByStatus(String status);
    
    @Query("SELECT p FROM Pet p WHERE p.status = 'AVAILABLE'")
    List<Pet> findAvailablePets();
    List<Pet> findByAdopter_Id(Long adopterId);
}