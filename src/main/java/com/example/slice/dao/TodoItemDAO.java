package com.example.slice.dao;

import com.example.slice.entity.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TodoItemDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createTodoItem(TodoItem todoItem) {
        try {
            String sql = "INSERT INTO todoitem(userid, name) VALUES(?, ?)";
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, todoItem.getUserid());
                    ps.setString(2, todoItem.getName());
                    return ps;
                }
            }, holder);
            int id = holder.getKey().intValue();
            return id;
        } catch (Exception exception) {
            return -1;
        }
    }

//    public List<TodoItem> findTodoByUser(int userid){
//
//    }
//
//    public List<TodoItem> findTodoByUser(String username){
//
//    }

    public int deleteTodoItem(int id) {
        try {
            jdbcTemplate.update("DELETE FROM todoitem WHERE id = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, id);
                        }
                    });
        } catch (Exception exception) {
            return 2;
        }
        return 1;
    }

//    public int changeName(int id, String name){
//
//    }
}
