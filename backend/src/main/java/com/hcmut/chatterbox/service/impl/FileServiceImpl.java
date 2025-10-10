package com.hcmut.chatterbox.service.impl;

import com.hcmut.chatterbox.constant.Constants;
import com.hcmut.chatterbox.constant.ErrorEnum;
import com.hcmut.chatterbox.dto.response.UploadFileResponseDTO;
import com.hcmut.chatterbox.exception.BizException;
import com.hcmut.chatterbox.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final GridFsTemplate gridFsTemplate;
    
    public UploadFileResponseDTO storeFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (!contentType.startsWith("image/") && !contentType.startsWith("video/")) {
            throw new BizException(ErrorEnum.INVALID_FILE_TYPE);
        }
        if (file.getSize() > Constants.FILE_MAX_SIZE) {
            throw new BizException(ErrorEnum.INVALID_FILE_SIZE);
        }
        String fileId = null;
        try {
            fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), contentType).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new UploadFileResponseDTO(fileId);
    }
}