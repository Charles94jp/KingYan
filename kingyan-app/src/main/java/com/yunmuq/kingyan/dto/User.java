package com.yunmuq.kingyan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

/**
 * 用户和角色是多对一，用户只能有唯一的角色。暂不支持多对多
 * {@link com.yunmuq.kingyan.model.response.UserInfoResponse}
 *
 * @author yunmuq
 * @version v1.0.0
 * @since 2022-04-21
 * @since 1.8
 * @since spring boot 2.6.6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private BigInteger id;
    private String name;
    private String password;
    private Role role;
    private String nickname;
    /**
     * 数据库中为char，0未知，1男，2女
     */
    private int sex;
    private String phone;
    private String email;
    private Date createdDate;
}
