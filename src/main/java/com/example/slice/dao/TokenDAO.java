package com.example.slice.dao;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.example.slice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TokenDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String TOKEN_SECRET = "UpfXcqnahyP0ID4L";

    private static String sign(String name) {
        try {
            String currentTime = String.valueOf(System.currentTimeMillis());
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");

            return JWT.create()
                    .withHeader(header)
                    .withClaim("name", name + currentTime)
                    .sign(algorithm);
        } catch (Exception exception) {
            return null;
        }
    }

    public String createToken(int userid, String username, String password) {
        String token = sign(username + password);

        try {
            jdbcTemplate.update("INSERT INTO token(userid, token) VALUES(?, ?)",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, userid);
                            preparedStatement.setString(2, token);
                        }
                    });
        } catch (DuplicateKeyException exception) {
            try {
                jdbcTemplate.update("UPDATE token SET token = ? WHERE userid = ?",
                        new PreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                                preparedStatement.setString(1, token);
                                preparedStatement.setInt(2, userid);
                            }
                        });
            } catch (Exception another_exception) {
                return null;
            }
            return token;
        } catch (Exception exception) {
            return null;
        }
        return token;
    }

    public int verifyToken(String token){
        try {
            Object[] params = new Object[]{token};
            String sql = "SELECT userid FROM token WHERE token = ?";
            int userid = jdbcTemplate.queryForObject(sql, params, Integer.class);

            return userid;
        } catch (Exception exception) {
            return -1;
        }
    }

    public int verifyPassword(String username, String password){
        try {
            Object[] params = new Object[]{username};
            String sql = "SELECT password FROM user WHERE username = ?";
            String realPassword = jdbcTemplate.queryForObject(sql, params, String.class);
            if (password.equals(realPassword)) {
                return 1;
            } else {
                return 2;
            }
        }catch (Exception exception){
            return 3;
        }
    }
}
