package com.example.slice.dao;

import com.example.slice.entity.ProjectTask;
import com.example.slice.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.*;

@Repository
public class ProjectTaskDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createProjectTask(ProjectTask projectTask, String[] usernames){
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
            if(id > 0){
                setTaskUserid(projectTask.getProjectid(), id, usernames);
            }else{
                return -2;
            }
            return id;
        }catch (Exception exception){
            return -1;
        }
    }

    public int deleteProjectTask(int id){
        try{
            if(findPostTask(id) != null && findPostTask(id).size() > 0){
                return -2;
            }
            jdbcTemplate.update("DELETE FROM project_task WHERE id = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, id);
                        }
                    });
        }catch (Exception exception){
            return -1;
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
            String sql_task = "SELECT taskid FROM task_member WHERE userid = ? AND projectid = ?";
            List<Integer> results = jdbcTemplate.queryForList(sql_task, params, Integer.class);

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
                    projectTask.setStarttime(resultSet.getString("starttime"));
                    projectTask.setEndtime(resultSet.getString("endtime"));
                    projectTask.setStatus(resultSet.getInt("status"));
                    projectTask.setChangetime(resultSet.getString("changetime"));
                    return projectTask;
                }
            });

            String sql_user = "SELECT userid FROM task_member WHERE taskid = ?";
            List<Integer> user = jdbcTemplate.queryForList(sql_user, params , Integer.class);
            projectTask.setUserid(user);

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
    public int setTask(int id, String description, String starttime, String endtime, String[] userid){
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

    public int setTaskUserid(int projectid, int taskid, String[] usernames){
        try{
            for(String username : usernames) {
                Object[] params = new Object[]{username};
                String sql_userid = "SELECT id FROM user WHERE username = ?";
                int userid = jdbcTemplate.queryForObject(sql_userid, params, Integer.class);

                String sql = "SELECT userid FROM project_member WHERE projectid = " + projectid;
                List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql);
                ArrayList<Integer> results = new ArrayList<>();

                for (Map<String, Object> i : rs) {
                    results.add(Integer.parseInt(i.get("userid").toString()));
                }

                if (!results.contains(userid)) {
                    return -2;
                }

                try {
                    jdbcTemplate.update("INSERT INTO task_member VALUES(?, ?, ?)",
                            new PreparedStatementSetter() {
                                @Override
                                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                                    preparedStatement.setInt(1, projectid);
                                    preparedStatement.setInt(2, taskid);
                                    preparedStatement.setInt(3, userid);
                                }
                            });
                }catch (DuplicateKeyException e){

                }
            }
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
                    int taskid = Integer.parseInt(i.get("id").toString());
                    Object[] params = new Object[]{taskid};
                    String sql_user = "SELECT userid FROM task_member WHERE taskid = ?";
                    List<Integer> userid = jdbcTemplate.queryForList(sql_user, params, Integer.class);
                    List<String> usernames = new ArrayList<>();
                    for(int id : userid){
                        String username = jdbcTemplate.queryForObject("SELECT username FROM user WHERE id = ?",
                                new Object[]{id}, String.class);
                        usernames.add(username);
                    }
                    i.put("users", usernames);

                    i.put("pretasks", findPreTask(taskid));
                }catch(Exception exception){
                    i.put("users", null);
                }
            }

            return rs;
        } catch (Exception exception) {
            return null;
        }
    }

    public List<ProjectTask> getTodaysTasks(int userid){
        String current_date = DateUtil.getCurrentDate();

        try{
            Object[] params = new Object[]{userid};
            String sql = "SELECT taskid FROM task_member WHERE userid = ?";
            List<Integer> taskid = jdbcTemplate.queryForList(sql, params, Integer.class);

            ArrayList<ProjectTask> results = new ArrayList<>();

            for(int id : taskid){
                ProjectTask pt = findTasksById(id);
                if(DateUtil.compareDate(pt.getStarttime(), current_date) == 0){
                    results.add(pt);
                }else if(DateUtil.compareDate(pt.getStarttime(), current_date) < 0){
                    if(DateUtil.compareDate(pt.getEndtime(), current_date) >= 0){
                        if(pt.getStatus() == 2){
                            results.add(pt);
                        }else if(pt.getStatus() == 3){
                            if(DateUtil.compareDate(pt.getChangetime(), current_date) == 0){
                                results.add(pt);
                            }
                        }
                    }
                }
            }

            return results;
        }catch (Exception exception){
            return null;
        }
    }

    public List<ProjectTask> getAllProjectTask(int userid){
        try{
            Object[] params = new Object[]{userid};
            String sql = "SELECT taskid FROM task_member WHERE userid = ?";
            List<Integer> taskid = jdbcTemplate.queryForList(sql, params, Integer.class);

            ArrayList<ProjectTask> results = new ArrayList<>();
            for(int id : taskid){
                ProjectTask pt = findTasksById(id);
                results.add(pt);
            }

            return results;
        }catch (Exception exception){
            return null;
        }
    }

    public int ChangeStatus(int taskid, int status, String time){
        try{
            String sql = "UPDATE project_task SET status = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, status);
                            preparedStatement.setInt(2, taskid);
                        }
                    });
            switch (status){
                case 3:
                case 4:
                    jdbcTemplate.update("UPDATE project_task SET changetime = ? WHERE id = ?",
                            new PreparedStatementSetter() {
                                @Override
                                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                                    preparedStatement.setString(1, time);
                                    preparedStatement.setInt(2, taskid);
                                }
                            });
                    break;
                case 6:
                    jdbcTemplate.update("UPDATE project_task SET endtime = ? WHERE id = ?",
                            new PreparedStatementSetter() {
                                @Override
                                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                                    preparedStatement.setString(1, time);
                                    preparedStatement.setInt(2, taskid);
                                }
                            });
                    break;
            }
        }catch (Exception exception){
            return 2;
        }
        return 1;
    }

    public int startTask(int taskid) {
        try {
            ProjectTask pt = findTasksById(taskid);
            int status = pt.getStatus();

            if (status == 1 || status == 6) {
                ChangeStatus(taskid, 2, "");
            } else {
                return -2;
            }
            return 1;
        } catch (Exception exception) {
            return -1;
        }
    }

    public int finishTask(int taskid, String time){
        try {
            ProjectTask pt = findTasksById(taskid);
            String starttime = pt.getStarttime();
            String endtime = pt.getEndtime();
            int status = pt.getStatus();

            if(DateUtil.compareTime(starttime, time) < 0 && DateUtil.compareTime(endtime, time) > 0){
                if(status == 2 || status == 6){
                    ChangeStatus(taskid, 3, time);
                }else{
                    return -3;
                }
            }
            else{
                return -2;
            }
            return 1;
        }catch (Exception exception){
            return -1;
        }
    }

    public int discardTask(int taskid, String time){
        try {
            ProjectTask pt = findTasksById(taskid);
            String starttime = pt.getStarttime();
            String endtime = pt.getEndtime();
            int status = pt.getStatus();

            if(DateUtil.compareTime(starttime, time) < 0 && DateUtil.compareTime(endtime, time) > 0){
                if(status == 2 || status == 6){
                    ChangeStatus(taskid, 4, time);
                }else{
                    return -3;
                }
            }else{
                return -2;
            }
            return 1;
        }catch (Exception exception){
            return -1;
        }
    }

    public int delayTask(int taskid){
        try {
            ProjectTask pt = findTasksById(taskid);
            int status = pt.getStatus();

            if (status == 2 || status == 6) {
                ChangeStatus(taskid, 5, "");
            } else {
                return -2;
            }
            return 1;
        } catch (Exception exception) {
            return -1;
        }
    }

    public int delayProjectTask(int taskid, String endtime){
        try{
            ProjectTask pt = findTasksById(taskid);
            int status = pt.getStatus();

            if(status == 1 || status == 2){
                ChangeStatus(taskid, 6, endtime);
            }else{
                return -2;
            }
            return 1;
        }catch (Exception exception){
            return -1;
        }
    }

    public HashMap<String, HashMap<String, Integer>> getData(int userid, String s, String e){
        Date start = DateUtil.stringToDate(s);
        Date end = DateUtil.stringToDate(e);

        Calendar c = Calendar.getInstance();
        c.setTime(start);

        HashMap<String, HashMap<String, Integer>> result = new HashMap<>();
        String current = DateUtil.dateToString(start);
        while(DateUtil.compareDate(current, e) <= 0) {
            HashMap<String, Integer> count = new HashMap<>();
            count.put("finish", 0);
            count.put("discard", 0);
            count.put("expire", 0);

            result.put(current, count);

            c.add(Calendar.DAY_OF_MONTH, 1);
            Date nextDay = c.getTime();
            current = DateUtil.dateToString(nextDay);
        }

        try{
            List<ProjectTask> pts = getAllProjectTask(userid);
            for(ProjectTask pt : pts){
                try {
                    int status = pt.getStatus();
                    if(status == 3){
                        String taskDate = pt.getChangetime().substring(0, 10);
                        int prev = result.get(taskDate).get("finish");
                        result.get(taskDate).put("finish", prev + 1);
                    }
                    if(status == 4){
                        String taskDate = pt.getChangetime().substring(0, 10);
                        int prev = result.get(taskDate).get("discard");
                        result.get(taskDate).put("discard", prev + 1);
                    }
                    if(status == 5){
                        String taskDate = pt.getEndtime().substring(0, 10);
                        int prev = result.get(taskDate).get("expire");
                        result.get(taskDate).put("expire", prev + 1);
                    }
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }
            }

            return result;
        }catch (Exception exception){
            return null;
        }
    }

    public int setPreProjecttask(int projectid, int taskid, int preid){
        List<Integer> ids = jdbcTemplate.queryForList("SELECT id FROM project_task WHERE projectid = ?",
                new Object[]{projectid}, Integer.class);
        if(!ids.contains(preid)){
            return -3;
        }

        Object[] params = new Object[]{taskid};
        String sql_pre = "SELECT pretaskid FROM pre_task WHERE taskid = ?";

        String pre_id = "";
        List<Integer> pre_tasks = new ArrayList<>();
        try {
            pre_id = jdbcTemplate.queryForObject(sql_pre, params, String.class);
            String[] ids_str = pre_id.split(" ");
            for(int i = 0; i < ids_str.length; i++){
                pre_tasks.add(Integer.parseInt(ids_str[i]));
            }
        }catch (EmptyResultDataAccessException exception){

        }

        String pre_task = "";
        if (pre_tasks.contains(preid)){
            return -2;
        }else{
            pre_task = pre_id + preid + " ";
        }
        try {
            params = new Object[]{projectid, taskid, pre_task};
            String sql = "INSERT INTO pre_task VALUES(?, ?, ?)";
            jdbcTemplate.update(sql, params);
            return 1;
        }catch (DuplicateKeyException exception){
            params = new Object[]{pre_task, taskid};
            String sql = "UPDATE pre_task SET pretaskid = ? WHERE taskid = ?";
            jdbcTemplate.update(sql, params);
            return 1;
        }catch (Exception exception){
            return -1;
        }
    }

    public List<ProjectTask> findPreTask(int taskid){
        try{
            Object[] params = new Object[]{taskid};
            String sql = "SELECT pretaskid FROM pre_task WHERE taskid = ?";
            try {
                String pre = jdbcTemplate.queryForObject(sql, params, String.class);
                List<ProjectTask> pres = new ArrayList<>();

                String[] ids = pre.split(" ");
                for(String id : ids){
                    int pre_taskid = Integer.parseInt(id);
                    ProjectTask pt = findTasksById(pre_taskid);
                    pres.add(pt);
                }

                return pres;
            }catch (EmptyResultDataAccessException exception){
                return new ArrayList<>();
            }
        }catch (Exception exception){
            return null;
        }
    }

    public List<ProjectTask> findPostTask(int taskid){
        try{
            String sql = "SELECT taskid, pretaskid FROM pre_task";
            List<ProjectTask> results = new ArrayList<>();

            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql);
            for(Map<String, Object> i : rs){
                int id = Integer.parseInt(i.get("taskid").toString());
                String[] pres = i.get("pretaskid").toString().split(" ");
                List<Integer> pretasks = new ArrayList<>();
                for(String s : pres){
                    pretasks.add(Integer.parseInt(s));
                }
                if(pretasks.contains(taskid)){
                    results.add(findTasksById(id));
                }
            }

            return results;
        }catch(Exception exception){
            return null;
        }
    }

    public Collection<Map<String, Object>> getProjectProgress(int projectid){
        try{
            Map<String, Map<String, Object>> results = new HashMap<>();

            Object[] params = new Object[]{projectid};
            String sql = "SELECT userid FROM project_member WHERE projectid = ?";
            List<Integer> users = jdbcTemplate.queryForList(sql, params, Integer.class);
            for(int userid : users){
                String username = jdbcTemplate.queryForObject("SELECT username FROM user WHERE id = ?",
                        new Object[]{userid}, String.class);
                Map<String, Object> m = new HashMap<>();
                m.put("username", username);
                m.put("All Tasks", 0);
                m.put("Finished Tasks", 0);
                m.put("Delayed Tasks", 0);
                m.put("Ongoing Tasks", 0);
                m.put("Unstarted Tasks", 0);

                results.put(username, m);
            }

            params = new Object[]{projectid};
            sql = "SELECT id, status FROM project_task WHERE projectid = ?";
            List<Map<String, Object>> tasks = jdbcTemplate.queryForList(sql, params);
            for(Map<String, Object> i : tasks){
                int id = Integer.parseInt(i.get("id").toString());
                int status = Integer.parseInt(i.get("status").toString());

                params = new Object[]{id};
                sql = "SELECT userid FROM task_member WHERE taskid = ?";
                List<Integer> members = jdbcTemplate.queryForList(sql, params, Integer.class);

                for(int user : members){
                    String username = jdbcTemplate.queryForObject("SELECT username FROM user WHERE id = ?",
                            new Object[]{user}, String.class);
                    int prev = (int)results.get(username).get("All Tasks");
                    results.get(username).put("All Tasks", prev + 1);

                    String keyword = "";
                    if(status == 1){
                        keyword = "Unstarted Tasks";
                    }else if(status == 2){
                        keyword = "Ongoing Tasks";
                    }else if(status == 3){
                        keyword = "Finished Tasks";
                    }else if(status == 6){
                        keyword = "Delayed Tasks";
                    }else{
                        continue;
                    }

                    int s_prev = (int)results.get(username).get(keyword);
                    results.get(username).put(keyword, s_prev + 1);
                }
            }

            return results.values();
        }catch (Exception exception){
            return null;
        }
    }
}

