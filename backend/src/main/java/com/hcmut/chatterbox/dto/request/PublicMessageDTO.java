package com.hcmut.chatterbox.dto.request;

import com.hcmut.chatterbox.entity.User;
import lombok.Data;

@Data
public class PublicMessageDTO {
    private User sender;
    private String content;
}
