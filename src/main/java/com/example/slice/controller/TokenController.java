package com.example.slice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.slice.entity.Token;
import com.example.slice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    TokenService tokenService;

    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public String getToken(@RequestBody JSONObject jsonObject){
        String username = jsonObject.get("username").toString();
        String password = jsonObject.get("password").toString();
        int userid = Integer.parseInt(jsonObject.get("userid").toString());

        return tokenService.createToken(userid, username, password);
    }
}
