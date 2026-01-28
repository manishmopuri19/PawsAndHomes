package com.PawsAndHomes.Backend.Service;


import org.springframework.web.multipart.MultipartFile;

import com.PawsAndHomes.Backend.Dto.Common.UploadResponseDTO;
import com.PawsAndHomes.Backend.Dto.Document.DocumentResponseDTO;

import java.util.List;

public interface DocumentService {
    UploadResponseDTO uploadPetDocument(MultipartFile file, Long petId);
    List<DocumentResponseDTO> getPetDocuments(Long petId);
}