package com.hcmut.chatterbox.controller;

import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.dto.response.UploadFileResponseDTO;
import com.hcmut.chatterbox.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;
    
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<UploadFileResponseDTO>> uploadFile(@RequestParam("file") MultipartFile file) {
        UploadFileResponseDTO response = fileService.storeFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }
}