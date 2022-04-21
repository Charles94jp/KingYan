package com.yunmuq.kingyan.mapper;

import com.yunmuq.kingyan.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectUserByUserName(String userName);

}
