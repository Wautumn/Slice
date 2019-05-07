package com.example.slice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.slice.entity.Token;
import com.example.slice.entity.User;
import com.example.slice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    //status code :
    //1 for success
    //2 for existing user
    //3 for insertion error
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public int register(@RequestBody JSONObject jsonObject) {
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
    //2 for wrong token
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public int login(@RequestBody JSONObject jsonObject) {
        int userid = Integer.parseInt(jsonObject.get("userid").toString());
        String token = jsonObject.get("token").toString();

        Token realToken = userService.findTokenById(userid);
        String realValue = realToken.getToken();

        if (token.equals(realValue)) {
            return 1;
        } else {
            return 2;
        }
    }

    @RequestMapping(value = "/findUserByName", method = RequestMethod.GET)
    public User findUserByName(String username) {
        User user = userService.findUserByName(username);
        return user;
    }

    @RequestMapping(value = "/findUserByEmail", method = RequestMethod.GET)
    public User findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        return user;
    }

    //result:
    //-1 for non-existent user
    //other value for user id
    @RequestMapping(value = "/findUserid", method = RequestMethod.GET)
    public int findUserid(String username) {
        int id = userService.findIdByName(username);
        return id;
    }

    //result:
    //String for user name
    //null for non-existent user
    @RequestMapping(value = "/findNameById", method = RequestMethod.GET)
    public String findNameById(int id){
        String username = userService.findNameById(id);
        return username;
    }

    //status code :
    //1 for success
    //2 for deletion error
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public int deleteUser(String username) {
        return userService.deleteUser(username);
    }
}
