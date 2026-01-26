package com.PawsAndHomes.Backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PawsAndHomes.Backend.Models.Document;

public interface docRepo extends JpaRepository<Document, Long> {
	
	List<Document> findByAdopterId(Long adopterId);

    List<Document> findByNgoId(Long ngoId);

}
