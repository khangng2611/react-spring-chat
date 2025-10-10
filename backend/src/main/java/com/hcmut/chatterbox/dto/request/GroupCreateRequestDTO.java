package com.hcmut.chatterbox.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class GroupCreateRequestDTO {
    private String name;
    private String createdBy;
    private List<String> participantIds;
}
