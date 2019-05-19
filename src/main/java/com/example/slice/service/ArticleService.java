package com.example.slice.service;

import com.example.slice.dao.ArticleDAO;
import com.example.slice.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    ArticleDAO articleDAO;

    public List<Article> getArticle(int userid){
        return articleDAO.getArticle(userid);
    }
}
