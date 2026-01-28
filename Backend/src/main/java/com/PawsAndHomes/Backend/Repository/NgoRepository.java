package com.PawsAndHomes.Backend.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PawsAndHomes.Backend.Models.Ngo;

@Repository
public interface NgoRepository extends JpaRepository<Ngo, Long> {

    Optional<Ngo> findByEmail(String email);
    
}
