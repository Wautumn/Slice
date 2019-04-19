package com.example.slice.dao;

import com.example.slice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createUser(User user){
        try {
            jdbcTemplate.update("INSERT INTO user(username, password, email) VALUES(?, ?, ?)",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, user.getUsername());
                            preparedStatement.setString(2, user.getPassword());
                            preparedStatement.setString(3, user.getEmail());
                        }
                    });
        }catch(DuplicateKeyException exception){
            return 2;   //user already exists
        }catch(Exception exception){
            return 3;   //insertion error
        }
        return 1;   //success
    }

    public User findUserByName(String username) {
        try {
            Object[] params = new Object[]{username};
            String sql = "SELECT * FROM user WHERE username = ?";
            User user = jdbcTemplate.queryForObject(sql, params, new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int paramInt)
                        throws SQLException {
                    User user = new User();
                    user.setId(Integer.parseInt(resultSet.getString("id")));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    return user;
                }
            });
            return user;
        } catch (Exception exception) {
            return null;
        }
    }

    public User findUserByEmail(String email){
        try {
            Object[] params = new Object[]{email};
            String sql = "SELECT * FROM user WHERE email = ?";
            User user = jdbcTemplate.queryForObject(sql, params, new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int paramInt)
                        throws SQLException {
                    User user = new User();
                    user.setId(Integer.parseInt(resultSet.getString("id")));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    return user;
                }
            });
            return user;
        } catch (Exception exception) {
            return null;
        }
    }

    public int deleteUser(String username){
        try {
            jdbcTemplate.update("DELETE FROM user WHERE username = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, username);
                        }
                    });
        }catch(Exception exception){
            return 2;   //deletion error
        }
        return 1;   //success
    }
}