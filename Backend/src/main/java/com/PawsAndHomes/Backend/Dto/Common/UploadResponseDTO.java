package com.PawsAndHomes.Backend.Dto.Common;


import lombok.Data;

@Data
public class UploadResponseDTO {
    private Long documentId;
    private String fileUrl;
    private String fileName;
    public UploadResponseDTO() {}

    // Getters and Setters
    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }
    
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}