package com.example.slice.service;

import com.example.slice.dao.TokenDAO;
import com.example.slice.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    private TokenDAO tokenDAO;

    public String createToken(int userid, String username, String password){
        return tokenDAO.createToken(userid, username, password);
    }
}
