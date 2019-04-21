package com.example.slice.service;

import com.example.slice.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void createUser(){
        User user = new User();

        user.setUsername("Test_User");
        user.setPassword("123456");
        userService.createUser(user);
        int i = user.getId();
        System.out.println(i);
    }
}
