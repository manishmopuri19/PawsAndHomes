package com.PawsAndHomes.Backend.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.PawsAndHomes.Backend.Dto.Common.UploadResponseDTO;
import com.PawsAndHomes.Backend.Dto.Document.DocumentResponseDTO;
import com.PawsAndHomes.Backend.Models.Document;
import com.PawsAndHomes.Backend.Models.Pet;
import com.PawsAndHomes.Backend.Repository.DocumentRepository;
import com.PawsAndHomes.Backend.Repository.PetRepository;
import com.PawsAndHomes.Backend.Service.DocumentService;
import com.PawsAndHomes.Backend.Service.S3Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentServiceImpl implements DocumentService {
    
    private final DocumentRepository documentRepository;
    private final PetRepository petRepository;
    private final S3Service s3Service;

    @Override
    public UploadResponseDTO uploadPetDocument(MultipartFile file, Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        
        try {
            String fileUrl = s3Service.uploadFile(file, "pets/documents");
            
            Document document = new Document();
            document.setFileName(file.getOriginalFilename());
            document.setFileUrl(fileUrl);
            document.setFileType(file.getContentType());
            document.setPet(pet);
            
            Document savedDocument = documentRepository.save(document);
            
            UploadResponseDTO response = new UploadResponseDTO();
            response.setDocumentId(savedDocument.getId());
            response.setFileUrl(savedDocument.getFileUrl());
            response.setFileName(savedDocument.getFileName());
            
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload document: " + e.getMessage());
        }
    }

    @Override
    public List<DocumentResponseDTO> getPetDocuments(Long petId) {
        List<Document> documents = documentRepository.findByPetId(petId);
        return documents.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    private DocumentResponseDTO convertToResponseDTO(Document document) {
        DocumentResponseDTO dto = new DocumentResponseDTO();
        dto.setId(document.getId());
        dto.setFileName(document.getFileName());
        dto.setFileUrl(document.getFileUrl());
        dto.setFileType(document.getFileType());
        dto.setUploadedAt(document.getUploadedAt().toString());
        return dto;
    }
}