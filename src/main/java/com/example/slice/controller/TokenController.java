package com.example.slice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.slice.entity.Token;
import com.example.slice.service.TokenService;
import com.example.slice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    TokenService tokenService;

    private UserService userService = new UserService();

    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public String getToken(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.get("username").toString();
        String password = jsonObject.get("password").toString();

        int userid = Integer.parseInt(jsonObject.get("userid").toString());
        if (tokenService.verifyPassword(username, password) == 1) {
            return tokenService.createToken(userid, username, password);
        } else {
            return "";
        }

    }

    //status code
    //-1 for non-existent token
    //other values for matching user id
    @RequestMapping(value = "verifyToken", method = RequestMethod.POST)
    public int verifyToken(@RequestBody JSONObject jsonObject) {
        String token = jsonObject.get("token").toString();

        return tokenService.verifyToken(token);
    }
}
