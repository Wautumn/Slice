package com.example.slice.controller;

import com.example.slice.entity.Article;
import com.example.slice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/getArticle", method = RequestMethod.GET)
    @ResponseBody
    public List<Article> getArticle(int userid){
        return articleService.getArticle(userid);
    }
}
