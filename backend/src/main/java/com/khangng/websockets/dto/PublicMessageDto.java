package com.khangng.websockets.dto;

import com.khangng.websockets.entity.User;
import lombok.Data;

@Data
public class PublicMessageDto {
    private User sender;
    private String content;
}
