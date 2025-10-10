package com.hcmut.chatterbox.controller;

import com.hcmut.chatterbox.dto.request.GroupCreateRequestDTO;
import com.hcmut.chatterbox.dto.request.OneToOneCreateRequestDTO;
import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.dto.response.UploadFileResponseDTO;
import com.hcmut.chatterbox.entity.Conversation;
import com.hcmut.chatterbox.service.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversations")
@AllArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;
    
    @PostMapping("/group")
    public ResponseEntity<ApiResponse<Conversation>> createGroup(@RequestBody GroupCreateRequestDTO request) {
        Conversation response = conversationService.createGroup(
                request.getName(),
                request.getCreatedBy(),
                request.getParticipantIds()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }
    
    @PostMapping("/one-to-one")
    public ResponseEntity<ApiResponse<Conversation>> createOneToOne(@RequestBody OneToOneCreateRequestDTO request) {
        Conversation response = conversationService.createOneToOne(
                request.getUserId1(),
                request.getUserId2()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }
}