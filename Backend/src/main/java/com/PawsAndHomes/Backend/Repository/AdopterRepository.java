package com.PawsAndHomes.Backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PawsAndHomes.Backend.Models.Adopter;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Long> {

    Optional<Adopter> findByEmail(String email);
}

