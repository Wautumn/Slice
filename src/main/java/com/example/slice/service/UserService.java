package com.example.slice.service;

import com.example.slice.dao.UserDAO;
import com.example.slice.entity.Token;
import com.example.slice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public int createUser(User user) {
        return userDAO.createUser(user);
    }

    public User findUserByName(String username) {
        return userDAO.findUserByName(username);
    }

    public User findUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    public int findIdByName(String username) {
        return userDAO.findIdByName(username);
    }

    public String findNameById(int id){return userDAO.findNameById(id);}

    public int deleteUser(String username) {
        return userDAO.deleteUser(username);
    }

    public Token findTokenById(int userid) {
        return userDAO.findToken(userid);
    }


}
