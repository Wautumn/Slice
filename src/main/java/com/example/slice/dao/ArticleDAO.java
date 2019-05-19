package com.example.slice.dao;

import com.example.slice.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class ArticleDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static ArrayList<Integer> indexes;

    public ArticleDAO(){
        indexes = new ArrayList<>();
        for(int i = 1; i < 15; i++){
            indexes.add(i);
        }
    }

    public List<Article> getArticle(int userid){
        List<Article> result = new ArrayList<>();
        try{
            Collections.shuffle(indexes);
            for(int i = 0; i < 3; i++){
                Object[] params = new Object[]{indexes.get(i)};
                String sql = "SELECT * FROM article WHERE id = ?";
                Article article = jdbcTemplate.queryForObject(sql, params, new RowMapper<Article>() {
                    @Override
                    public Article mapRow(ResultSet resultSet, int i) throws SQLException {
                        Article article = new Article();
                        article.setId(resultSet.getInt("id"));
                        article.setTitle(resultSet.getString("title"));
                        article.setContent(resultSet.getString("content"));
                        article.setAuthor(resultSet.getString("author"));
                        article.setImage(resultSet.getString("image"));
                        return article;
                    }
                });
                result.add(article);
            }
            return result;
        }catch (Exception exception){
            return null;
        }
    }
}
