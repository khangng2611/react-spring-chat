package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.request.MessageRequestDTO;

public interface MessageService {
    void sendMessage (MessageRequestDTO message);
}
