package com.PawsAndHomes.Backend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.PawsAndHomes.Backend.Dto.Common.UploadResponseDTO;
import com.PawsAndHomes.Backend.Dto.Document.DocumentResponseDTO;
import com.PawsAndHomes.Backend.Service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    
    private final DocumentService documentService;
    
    @PostMapping("/upload")
    @PreAuthorize("hasRole('NGO')")
    public ResponseEntity<UploadResponseDTO> uploadPetDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("petId") Long petId) {
        
        UploadResponseDTO response = documentService.uploadPetDocument(file, petId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasAnyRole('ADOPTER', 'NGO')")
    public ResponseEntity<List<DocumentResponseDTO>> getPetDocuments(@PathVariable Long petId) {
        return ResponseEntity.ok(documentService.getPetDocuments(petId));
    }
}
