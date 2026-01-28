package com.PawsAndHomes.Backend.Service.impl;

import java.io.IOException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.PawsAndHomes.Backend.Service.S3Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class S3ServiceImpl implements S3Service {
    
    private final S3Client s3Client;
    
    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    
    @Override
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        String fileName = Instant.now().getEpochSecond() + "_" + file.getOriginalFilename();
        String key = folder + "/" + fileName;
        
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();
        
        PutObjectResponse response = s3Client.putObject(putObjectRequest, 
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toString();
    }

    @Override
    public void deleteFile(String filePath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}