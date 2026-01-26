package com.PawsAndHomes.Backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PawsAndHomes.Backend.Models.Adopter;

public interface AdopterRepo extends JpaRepository<Adopter,Long> {
	Optional<Adopter> findByEmail(String email);

	boolean existsByEmail(String email);
}
