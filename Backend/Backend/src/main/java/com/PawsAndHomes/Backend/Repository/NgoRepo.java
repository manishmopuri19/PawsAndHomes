package com.PawsAndHomes.Backend.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.PawsAndHomes.Backend.Models.Ngo;

public interface NgoRepo extends JpaRepository<Ngo, Long> {
	Optional<Ngo> findByEmail(String email);

	boolean existsByEmail(String email);
}
