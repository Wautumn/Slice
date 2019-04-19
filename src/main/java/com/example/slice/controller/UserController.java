package com.example.slice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.slice.dao.UserDAO;
import com.example.slice.entity.User;
import com.example.slice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController{
    @Autowired
    UserService userService;

    //status code :
    //1 for success
    //2 for existing user
    //3 for insertion error
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public int register(@RequestBody JSONObject jsonObject){
        User user = new User();

        String username = jsonObject.get("username").toString();
        String password = jsonObject.get("password").toString();
        String email = jsonObject.get("email").toString();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        return userService.createUser(user);
    }

    //status code :
    //1 for success
    //2 for wrong password
    //3 for non-existent user
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public int login(@RequestBody JSONObject jsonObject){
        String username = jsonObject.get("username").toString();
        String password = jsonObject.get("password").toString();

        User user = userService.findUserByName(username);
        if(user == null) {
            return 2;
        }else if(!user.getPassword().equals(password)){
            return 3;
        }
        return 1;
    }

    @RequestMapping(value = "/findUserByName", method = RequestMethod.GET)
    public User findUserByName(String name){
        User user = userService.findUserByName(name);
        return user;
    }

    @RequestMapping(value = "/findUserByEmail", method = RequestMethod.GET)
    public User findUserByEmail(String email){
        User user = userService.findUserByEmail(email);
        return user;
    }


    //status code :
    //1 for success
    //2 for deletion error
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public int deleteUser(String username){
        return userService.deleteUser(username);
    }
}
