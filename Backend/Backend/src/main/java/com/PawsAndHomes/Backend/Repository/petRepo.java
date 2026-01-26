package com.PawsAndHomes.Backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PawsAndHomes.Backend.Models.Pet;

public interface petRepo extends JpaRepository<Pet, Long> {
	 	List<Pet> findByAdopterId(Long adopterId);

	    List<Pet> findByNgoId(Long ngoId);

}
