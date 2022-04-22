package com.yunmuq.kingyan.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yunmuq
 * @version v1.0.0
 * @since 2022-04-22
 * @since 1.8
 * @since spring boot 2.6.6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    boolean success;
    // 成功无提示直接跳转，失败是错误
    // String message;
    // if success
    CommonErrorResponse error;
    String csrfToken;
}
