package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.dto.response.UploadFileResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    ApiResponse<UploadFileResponseDTO> storeFile(MultipartFile file);
}