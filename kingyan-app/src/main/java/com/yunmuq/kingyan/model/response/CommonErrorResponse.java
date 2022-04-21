package com.yunmuq.kingyan.model.response;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class CommonErrorResponse {
    private int errorCode;
    private String massage;
    private String exception;

    public CommonErrorResponse() {
    }

    public CommonErrorResponse(int errorCode, String massage) {
        this.errorCode = errorCode;
        this.massage = massage;
        this.exception = null;
    }

    public CommonErrorResponse(int errorCode, String massage, @Nullable Exception e) {
        this.errorCode = errorCode;
        this.massage = massage;
        if (e != null) this.exception = e.toString();
    }
}
