package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.response.UploadFileResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    UploadFileResponseDTO storeFile(MultipartFile file);
}