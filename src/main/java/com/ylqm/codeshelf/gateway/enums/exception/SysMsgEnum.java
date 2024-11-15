package com.ylqm.codeshelf.gateway.enums.exception;

import lombok.Getter;

public enum SysMsgEnum {

    SYS_ERROR("X1000500", "系统内部错误！"),
    SYS_IO_ERROR("X1000501", "系统IO异常！");

    @Getter
    private final String code;

    @Getter
    private final String msg;

    SysMsgEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
