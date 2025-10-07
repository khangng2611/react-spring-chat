package com.hcmut.chatterbox.exception;

import com.hcmut.chatterbox.constant.ErrorEnum;
import lombok.Data;

@Data
public class BizException extends RuntimeException {
    private final ErrorEnum errorEnum;
    
    public BizException(ErrorEnum errorEnum) {
        super();
        this.errorEnum = errorEnum;
    }
}
