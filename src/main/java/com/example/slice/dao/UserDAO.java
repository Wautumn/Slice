package com.example.slice.dao;

import com.example.slice.entity.Token;
import com.example.slice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createUser(User user) {
        try {
            String sql = "INSERT INTO user(username, password, email) VALUES(?, ?, ?)";
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                                    @Override
                                    public PreparedStatement createPreparedStatement(Connection con)
                                            throws SQLException {
                                        PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                                        ps.setString(1, user.getUsername());
                                        ps.setString(2, user.getPassword());
                                        ps.setString(3, user.getEmail());
                                        return ps;
                                    }
                                }, holder);
//            int id = holder.getKey().intValue();
        } catch (DuplicateKeyException exception) {
            return 2;   //user already exists
        } catch (Exception exception) {
            return 3;   //insertion error
        }
        return 1;   //success
    }

    public User findUserByName(String username) {
        try {
            Object[] params = new Object[]{username};
            String sql = "SELECT id, email FROM user WHERE username = ?";
            User user = jdbcTemplate.queryForObject(sql, params, new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int paramInt)
                        throws SQLException {
                    User user = new User();
                    user.setId(Integer.parseInt(resultSet.getString("id")));
                    user.setUsername(username);
                    user.setEmail(resultSet.getString("email"));
                    return user;
                }
            });
            return user;
        } catch (Exception exception) {
            return null;
        }
    }

    public User findUserByEmail(String email) {
        try {
            Object[] params = new Object[]{email};
            String sql = "SELECT id, username FROM user WHERE email = ?";
            User user = jdbcTemplate.queryForObject(sql, params, new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int paramInt)
                        throws SQLException {
                    User user = new User();
                    user.setId(Integer.parseInt(resultSet.getString("id")));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(email);
                    return user;
                }
            });
            return user;
        } catch (Exception exception) {
            return null;
        }
    }

    public int findIdByName(String username) {
        try {
            Object[] params = new Object[]{username};
            String sql = "SELECT id FROM user WHERE username = ?";
            int id = jdbcTemplate.queryForObject(sql, params, Integer.class);
            return id;
        } catch (Exception exception) {
            return -1;
        }
    }

    public int deleteUser(String username) {
        try {
            jdbcTemplate.update("DELETE FROM user WHERE username = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, username);
                        }
                    });
        } catch (Exception exception) {
            return 2;   //deletion error
        }
        return 1;   //success
    }

    public Token findToken(int userid) {
        try {
            Object[] params = new Object[]{userid};
            String sql = "SELECT * FROM token WHERE userid = ?";
            Token token = jdbcTemplate.queryForObject(sql, params, new RowMapper<Token>() {
                public Token mapRow(ResultSet resultSet, int paramInt)
                        throws SQLException {
                    Token token = new Token();
                    token.setUserid(userid);
                    token.setToken(resultSet.getString("token"));
                    return token;
                }
            });
            return token;
        } catch (Exception exception) {
            return null;
        }
    }


}