package com.example.slice.dao;

import com.example.slice.entity.Project;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProjectDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createProject(Project project, String[] members, String[] tasks) {
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
                int task_result = addTasks(id, tasks);
                if (mem_result == 0 || task_result == 0) {
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

    public int deleteProject(int id) {
        try {
            jdbcTemplate.update("DELETE FROM project WHERE id = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, id);
                        }
                    });
        } catch (Exception exception) {
            return -1;
        }
        return 1;
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
            return -1;
        }
        return 1;
    }

    public int addTasks(int projectid, String[] tasks){
        try{
            for(String s : tasks){
                jdbcTemplate.update("INSERT INTO project_task(name, projectid) VALUES(?, ?)",
                        new PreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                                preparedStatement.setString(1, s);
                                preparedStatement.setInt(2, projectid);
                            }
                        });
            }
        }catch (Exception exception){
            return 0;
        }
        return 1;
    }

    public List<Project> findProjectByUser(int userid){
        try{
            Object[] params = new Object[]{userid};
            String sql = "SELECT * FROM project WHERE userid = ?";

            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<Project> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                Project project = new Project();
                project.setId(Integer.parseInt(i.get("id").toString()));
                project.setName(i.get("name").toString());
                project.setDescription(i.get("description").toString());
                project.setStarttime(i.get("starttime").toString());
                project.setEndtime(i.get("endtime").toString());
                project.setUserid(Integer.parseInt(i.get("userid").toString()));
                project.setStatus(Integer.parseInt(i.get("status").toString()));

                results.add(project);
            }

            return results;
        }catch (Exception exception){
            return null;
        }
    }

    public List<Project> findProjectAttend(int userid){
        try{
            Object[] params = new Object[]{userid};
            String sql = "SELECT * FROM project WHERE id in (SELECT id FROM project_member WHERE userid = ? " +
                    "AND userid NOT IN (SELECT userid FROM project WHERE project.id = project_member.projectid))";

            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<Project> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                Project project = new Project();
                project.setId(Integer.parseInt(i.get("id").toString()));
                project.setName(i.get("name").toString());
                project.setDescription(i.get("description").toString());
                project.setStarttime(i.get("starttime").toString());
                project.setEndtime(i.get("endtime").toString());
                project.setUserid(Integer.parseInt(i.get("userid").toString()));
                project.setStatus(Integer.parseInt(i.get("status").toString()));

                results.add(project);
            }

            return results;
        }catch (Exception exception){
            return null;
        }
    }

    public List<String> findUserByProject(int projectid){
        try{
            Object[] params = new Object[]{projectid};
            String sql = "SELECT username FROM user WHERE id IN (SELECT userid FROM project_member WHERE projectid = ?)";
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<String> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                results.add(i.get("username").toString());
            }

            return results;
        }catch (Exception exception){
            return null;
        }
    }

    public List<Integer> findAllTasks(int projectid){
        try{
            Object[] params = new Object[]{projectid};
            String sql = "SELECT id FROM project_task WHERE projectid = ?";
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<Integer> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                results.add(Integer.parseInt(i.get("id").toString()));
            }

            return results;
        }catch (Exception exception){
            return null;
        }
    }

    public List<Integer> findProjectByName(String name){
        try{
            Object[] params = new Object[]{"%" + name + "%"};
            String sql = "SELECT id FROM project WHERE name LIKE ?";
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<Integer> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                results.add(Integer.parseInt(i.get("id").toString()));
            }

            return results;
        }catch (Exception exception){
            return null;
        }
    }

    public Project findProjectById(int id){
        try{
            Object[] params = new Object[]{id};
            String sql = "SELECT * FROM project WHERE id = ?";
            Project project = jdbcTemplate.queryForObject(sql, params, new RowMapper<Project>() {
                @Override
                public Project mapRow(ResultSet resultSet, int i) throws SQLException {
                    Project project = new Project();
                    project.setId(resultSet.getInt("id"));
                    project.setName(resultSet.getString("name"));
                    project.setDescription(resultSet.getString("description"));
                    project.setUserid(resultSet.getInt("userid"));
                    project.setStarttime(resultSet.getString("starttime"));
                    project.setEndtime(resultSet.getString("endtime"));
                    project.setStatus(resultSet.getInt("status"));
                    return project;
                }
            });
            return project;
        }catch (Exception exception){
            return null;
        }
    }

    public int setProjectName(int id, String name){
        return setProjectContent(id, "name", name);
    }

    public int setProjectDescription(int id, String description){
        return setProjectContent(id, "description", description);
    }

    public int setStarttime(int id, String starttime){
        return setProjectContent(id, "starttime", starttime);
    }

    public int setEndtime(int id, String endtime){
        return setProjectContent(id, "endtime", endtime);
    }

    private int setProjectContent(int id, String attribute, String content){
        String sql = "";
        switch (attribute){
            case "name":
                sql = "UPDATE project SET name = ? WHERE id = ?";
                break;
            case "description":
                sql = "UPDATE project SET description = ? WHERE id = ?";
                break;
            case "starttime":
                sql = "UPDATE project SET starttime = ? WHERE id = ?";
                break;
            case "endtime":
                sql = "UPDATE project SET endtime = ? WHERE id = ?";
                break;
        }
        try{
            jdbcTemplate.update(sql,
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, content);
                            preparedStatement.setInt(2, id);
                        }
                    });
        }catch (Exception exception){
            return 2;
        }
        return 1;
    }
}

