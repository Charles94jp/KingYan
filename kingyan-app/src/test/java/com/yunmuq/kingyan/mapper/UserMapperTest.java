package com.yunmuq.kingyan.mapper;

import com.yunmuq.kingyan.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testSelectUserByUserName(){
        User admin = userMapper.selectUserByUserName("admin");
        System.out.println(admin);
        assertNotNull(admin.getRole().getName());
        assertNotNull(admin.getPassword());
    }
}