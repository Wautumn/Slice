package com.example.slice.dao;

import com.example.slice.entity.ProjectTask;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProjectTaskDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createProjectTask(ProjectTask projectTask){
        try{
            String sql = "INSERT INTO project_task(name, description, starttime, endtime, projectid)" +
                    " VALUES(?, ?, ?, ?, ?)";
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, projectTask.getName());
                    ps.setString(2, projectTask.getDescription());
                    ps.setString(3, projectTask.getStarttime());
                    ps.setString(4, projectTask.getEndtime());
                    ps.setInt(5, projectTask.getProjectid());
                    return ps;
                }
            }, holder);
            int id = holder.getKey().intValue();
            return id;
        }catch (Exception exception){
            return -1;
        }
    }

    public int deleteProjectTask(int id){
        try{
            jdbcTemplate.update("DELETE FROM project_task WHERE id = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, id);
                        }
                    });
        }catch (Exception exception){
            return 2;
        }
        return 1;
    }

    public int findProjectTaskId(int projectid, String name){
        try {
            Object[] params = new Object[]{projectid, name};
            String sql = "SELECT id FROM project_task WHERE projectid = ? and name = ?";
            int id = jdbcTemplate.queryForObject(sql, params, Integer.class);
            return id;
        }catch (Exception exception){
            return -1;
        }
    }

    public List<Integer> findTasksByUser(int userid, int projectid){
        try{
            Object[] params = new Object[]{userid, projectid};
            String sql = "SELECT id FROM project_task WHERE userid = ? and projectid = ?";
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

    public ProjectTask findTasksById(int id){
        try{
            Object[] params = new Object[]{id};
            String sql = "SELECT * FROM project_task WHERE id = ?";
            ProjectTask projectTask = jdbcTemplate.queryForObject(sql, params, new RowMapper<ProjectTask>() {
                @Override
                public ProjectTask mapRow(ResultSet resultSet, int i) throws SQLException {
                    ProjectTask projectTask = new ProjectTask();
                    projectTask.setId(resultSet.getInt("id"));
                    projectTask.setName(resultSet.getString("name"));
                    projectTask.setDescription(resultSet.getString("description"));
                    projectTask.setProjectid(resultSet.getInt("projectid"));
                    projectTask.setUserid(resultSet.getInt("userid"));
                    projectTask.setStarttime(resultSet.getString("starttime"));
                    projectTask.setEndtime(resultSet.getString("endtime"));
                    projectTask.setStatus(resultSet.getInt("status"));
                    return projectTask;
                }
            });
            return projectTask;
        }catch (Exception exception){
            return null;
        }
    }

    public List<Integer> findTasksByName(String name, int projectid) {
        try {
            Object[] params = new Object[]{"%" + name + "%", projectid};
            String sql = "SELECT id FROM project_task WHERE name LIKE ? AND projectid = ?";

            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<Integer> results = new ArrayList<>();

            for (Map<String, Object> i : rs) {
                results.add(Integer.parseInt(i.get("id").toString()));
            }
            return results;
        } catch (Exception exception) {
            return null;
        }
    }

    public int findProjectId(int id){
        try {
            Object[] params = new Object[]{id};
            String sql = "SELECT projectid FROM project_task WHERE id = ?";
            int projectid = jdbcTemplate.queryForObject(sql, params, Integer.class);
            return projectid;
        }catch (Exception exception){
            return -1;
        }
    }

    //id, name, description, starttime, endtime, projectid, userid, status
    public int setTask(int id, String description, String starttime, String endtime, int userid){
        int result_d = setTaskDescription(id, description);
        if(result_d != 1){
            return -1;
        }

        int result_s = setStarttime(id, starttime);
        if(result_s != 1){
            return -1;
        }

        int result_e = setEndtime(id, endtime);
        if(result_e != 1){
            return -1;
        }

//        int result_u = setTaskUserid(id, userid);
//        if(result_u != 1){
//            return -1;
//        }

        return 1;
    }

    public int setTaskName(int id, String name){
        return setTaskContent(id, "name", name);
    }

    public int setTaskDescription(int id, String description){
        return setTaskContent(id, "description", description);
    }

    public int setStarttime(int id, String starttime){
        return setTaskContent(id, "starttime", starttime);
    }

    public int setEndtime(int id, String endtime){
        return setTaskContent(id, "endtime", endtime);
    }

    public int setTaskUserid(int projectid, int taskid, String username){
        try{
            Object[] params = new Object[]{username};
            String sql_userid = "SELECT id FROM user WHERE username = ?";
            int userid = jdbcTemplate.queryForObject(sql_userid, params, Integer.class);

            String sql = "SELECT userid FROM project_member WHERE projectid = " + projectid;
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql);
            ArrayList<Integer> results = new ArrayList<>();

            for(Map<String, Object> i : rs) {
                results.add(Integer.parseInt(i.get("userid").toString()));
            }

            if(!results.contains(userid)){
                return -2;
            }
            jdbcTemplate.update("UPDATE project_task SET userid = ? WHERE id = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, userid);
                            preparedStatement.setInt(2, taskid);
                        }
                    });
        }catch (Exception exception){
            return -1;
        }
        return 1;
    }

    private int setTaskContent(int id, String attribute, String content){
        String sql = "";
        switch (attribute){
            case "name":
                sql = "UPDATE project_task SET name = ? WHERE id = ?";
                break;
            case "description":
                sql = "UPDATE project_task SET description = ? WHERE id = ?";
                break;
            case "starttime":
                sql = "UPDATE project_task SET starttime = ? WHERE id = ?";
                break;
            case "endtime":
                sql = "UPDATE project_task SET endtime = ? WHERE id = ?";
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

    public List<Map<String, Object>> getDistributeTask(int projectid) {
        try {
            String sql = "SELECT * FROM project_task WHERE projectid = " + projectid;
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql);

            for (Map<String, Object> i : rs) {
                try {
                    int userid = Integer.parseInt(i.get("userid").toString());
                    String sql_username = "SELECT username FROM user WHERE id = " + userid;
                    String username = jdbcTemplate.queryForObject(sql_username, String.class);
                    i.put("username", username);
                }catch(Exception exception){
                    i.put("username", "");
                }
            }

            return rs;
        } catch (Exception exception) {
            return null;
        }
    }
}

