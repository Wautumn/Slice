package com.example.slice.dao;

import com.example.slice.entity.Project;
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

@Repository
public class ProjectDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createProject(Project project, String[] members) {
        try {
            String sql_project = "INSERT INTO project(userid, name, description, starttime, endtime) " +
                    "VALUES(?, ?, ?, ?, ?)";
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql_project, new String[]{"id"});
                    ps.setInt(1, project.getUserid());
                    ps.setString(2, project.getName());
                    ps.setString(3, project.getDescription());
                    ps.setString(4, project.getStarttime());
                    ps.setString(5, project.getEndtime());
                    return ps;
                }
            }, holder);
            int id = holder.getKey().intValue();

            if (id > 0) {

                jdbcTemplate.update("INSERT INTO project_member(projectid, userid) VALUES(?, ?)",
                        new PreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                                preparedStatement.setInt(1, id);
                                preparedStatement.setInt(2, project.getUserid());
                            }
                        });


                int mem_result = addMembers(id, members);
                if (mem_result == 0) {
                    String sql = "DELETE FROM project WHERE id = " + id;
                    jdbcTemplate.update(sql);
                    return -1;
                }
                return id;
            }
        } catch (Exception exception) {
            return -1;
        }
        return -1;
    }

    public int addMembers(int projectid, String[] members) {
        try {
            for (String s : members) {
                Object[] params = new Object[]{s};
                String sql = "SELECT id FROM user WHERE username = ?";
                int mem_id = jdbcTemplate.queryForObject(sql, params, Integer.class);

                try {
                    jdbcTemplate.update("INSERT INTO project_member(projectid, userid) VALUES(?, ?)",
                            new PreparedStatementSetter() {
                                @Override
                                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                                    preparedStatement.setInt(1, projectid);
                                    preparedStatement.setInt(2, mem_id);
                                }
                            });
                } catch (DuplicateKeyException exception) {

                }
            }
        } catch (Exception exception) {
            return 0;
        }
        return 1;
    }
}

