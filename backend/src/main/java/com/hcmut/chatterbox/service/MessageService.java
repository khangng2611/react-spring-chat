package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.request.MessageRequestDTO;
import com.hcmut.chatterbox.entity.Message;

public interface MessageService {
    Message save (MessageRequestDTO message);
}
