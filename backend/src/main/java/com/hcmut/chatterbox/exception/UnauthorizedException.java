package com.hcmut.chatterbox.exception;

import com.hcmut.chatterbox.constant.ErrorEnum;
import lombok.Data;

@Data
public class UnauthorizedException extends RuntimeException {
    private final ErrorEnum errorEnum;
    
    public UnauthorizedException(ErrorEnum errorEnum) {
        super();
        this.errorEnum = errorEnum;
    }
}
