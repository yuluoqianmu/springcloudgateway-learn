package com.ylqm.codeshelf.gateway.exception;

import com.ylqm.codeshelf.gateway.enums.exception.SysMsgEnum;
import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private SysMsgEnum sysMsgEnum;

    public CustomException() {}
    public CustomException(String message) {
        this.message = message;
    }
    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public CustomException(Throwable cause) {}

    public CustomException(SysMsgEnum sysMsgEnum, String message) {
        this.code = sysMsgEnum.getCode();
        this.message = message;
    }


    public CustomException(SysMsgEnum sysMsgEnum) {
        this.code = sysMsgEnum.getCode();
        this.message = sysMsgEnum.getMsg();
    }

}
