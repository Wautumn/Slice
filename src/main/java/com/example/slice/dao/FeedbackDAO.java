package com.example.slice.dao;

import com.example.slice.entity.FeedBack;
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
public class FeedbackDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //Normal User
    public int createFeedback(FeedBack feedBack){
        try{
            String sql = "INSERT INTO feedback(title, content, userid, settime) VALUES(?, ?, ?, ?)";
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, feedBack.getTitle());
                    ps.setString(2, feedBack.getContent());
                    ps.setInt(3, feedBack.getUserid());
                    ps.setString(4, feedBack.getSettime());
                    return ps;
                }
            }, holder);
            int id = holder.getKey().intValue();
            return id;
        }catch(Exception exception){
            return -1;
        }
    }

    public int deleteFeedback(int id){
        try{
            jdbcTemplate.update("DELETE FROM feedback WHERE id = ?",
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

    public int setTitle(int id, String title){
        if(getStatus(id) != 1){
            return 3;
        }
        try{
            jdbcTemplate.update("UPDATE feedback SET title = ? WHERE id = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, title);
                            preparedStatement.setInt(2, id);
                        }
                    });
        }catch (Exception exception){
            return 2;
        }
        return 1;
    }

    public int setContent(int id, String content){
        if(getStatus(id) != 1){
            return 3;
        }
        try{
            jdbcTemplate.update("UPDATE feedback SET content = ? WHERE id = ?",
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

    public int getStatus(int id){
        try{
            Object[] params = new Object[]{id};
            String sql = "SELECT status FROM feedback WHERE id = ?";
            int status = jdbcTemplate.queryForObject(sql, params, Integer.class);
            return status;
        }catch (Exception exception){
            return -1;
        }
    }

    public List<FeedBack> findFeedbackByUser(int userid){
        try{
            Object[] params = new Object[]{userid};
            String sql = "SELECT * FROM feedback WHERE userid = ?";
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<FeedBack> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                FeedBack feedBack = new FeedBack();
                feedBack.setId(Integer.parseInt(i.get("id").toString()));
                feedBack.setContent(i.get("content").toString());
                feedBack.setTitle(i.get("title").toString());
                feedBack.setSettime(i.get("settime").toString());
                feedBack.setUserid(Integer.parseInt(i.get("userid").toString()));
                feedBack.setStatus(Integer.parseInt(i.get("status").toString()));
                if(feedBack.getStatus() == 2) {
                    feedBack.setReplytime(i.get("replytime").toString());
                    feedBack.setAnswer(i.get("answer").toString());
                }
                results.add(feedBack);
            }
            return results;
        }catch(Exception exception){
            return null;
        }
    }

    public FeedBack findFeedbackById(int id){
        try{
            Object[] params = new Object[]{id};
            String sql = "SELECT * FROM feedback WHERE id = ?";
            FeedBack feedBack = jdbcTemplate.queryForObject(sql, params, new RowMapper<FeedBack>() {
                @Override
                public FeedBack mapRow(ResultSet resultSet, int i) throws SQLException {
                    FeedBack feedback = new FeedBack();
                    feedback.setId(resultSet.getInt("id"));
                    feedback.setTitle(resultSet.getString("title"));
                    feedback.setContent(resultSet.getString("content"));
                    feedback.setUserid(resultSet.getInt("userid"));
                    feedback.setStatus(resultSet.getInt("status"));
                    feedback.setAnswer(resultSet.getString("answer"));
                    feedback.setSettime(resultSet.getString("settime"));
                    feedback.setReplytime(resultSet.getString("replytime"));
                    return feedback;
                }
            });
            return feedBack;
        }catch (Exception exception){
            return null;
        }
    }

    public List<Integer> findFeedbackByTitle(int userid, String title){
        try{
            Object[] params = new Object[]{userid, "%" + title + "%"};
            String sql = "SELECT id FROM feedback WHERE userid = ? AND title LIKE ?";
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<Integer> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                results.add(Integer.parseInt(i.get("id").toString()));
            }
            return results;
        }catch(Exception exception){
            return null;
        }
    }

    public List<Integer> findFeedbackBySettime(int userid, String settime){
        try{
            Object[] params = new Object[]{userid, settime};
            String sql = "SELECT id FROM feedback WHERE userid = ? AND settime = ?";
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<Integer> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                results.add(Integer.parseInt(i.get("id").toString()));
            }
            return results;
        }catch(Exception exception){
            return null;
        }
    }

    public List<Integer> findFeedbackByStatus(int userid, int status){
        try{
            Object[] params = new Object[]{userid, status};
            String sql = "SELECT id FROM feedback WHERE userid = ? AND status = ?";
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<Integer> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                results.add(Integer.parseInt(i.get("id").toString()));
            }
            return results;
        }catch(Exception exception){
            return null;
        }
    }

    //Administrator
    public List<FeedBack> findAllFeedback(){
        try{
            String sql = "SELECT * FROM feedback";
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql);
            ArrayList<FeedBack> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                FeedBack feedback = new FeedBack();

                feedback.setId(Integer.parseInt(i.get("id").toString()));
                feedback.setTitle(i.get("title").toString());
                feedback.setContent(i.get("content").toString());
                feedback.setUserid(Integer.parseInt(i.get("userid").toString()));
                feedback.setStatus(Integer.parseInt(i.get("status").toString()));

                if(feedback.getStatus() == 2) {
                    feedback.setAnswer(i.get("answer").toString());
                    feedback.setSettime(i.get("settime").toString());
                    feedback.setReplytime(i.get("replytime").toString());
                }
                results.add(feedback);
            }
            return results;
        }catch(Exception exception){
            return null;
        }
    }

    public List<FeedBack> findFeedbackByStatus(int status){
        try{
            Object[] params = new Object[]{status};
            String sql = "SELECT * FROM feedback WHERE status = ?";
            List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql, params);
            ArrayList<FeedBack> results = new ArrayList<>();

            for(Map<String, Object> i : rs){
                FeedBack feedback = new FeedBack();

                feedback.setId(Integer.parseInt(i.get("id").toString()));
                feedback.setTitle(i.get("title").toString());
                feedback.setContent(i.get("content").toString());
                feedback.setUserid(Integer.parseInt(i.get("userid").toString()));
                feedback.setStatus(Integer.parseInt(i.get("status").toString()));
                if(status == 2) {
                    feedback.setAnswer(i.get("answer").toString());
                    feedback.setSettime(i.get("settime").toString());
                    feedback.setReplytime(i.get("replytime").toString());
                }
                results.add(feedback);
            }
            return results;
        }catch(Exception exception){
            return null;
        }
    }

    public int setStatus(int id, int status){
        if(getStatus(id) != 1){
            return 3;
        }
        try{
            jdbcTemplate.update("UPDATE feedback SET status = ? WHERE id = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, status);
                            preparedStatement.setInt(2, id);
                        }
                    });
        }catch (Exception exception){
            return 2;
        }
        return 1;
    }

    public int setAnswer(int id, String answer){
        if(getStatus(id) != 1){
            return 3;
        }
        try{
            jdbcTemplate.update("UPDATE feedback SET answer = ? WHERE id = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, answer);
                            preparedStatement.setInt(2, id);
                        }
                    });
        }catch (Exception exception){
            return 2;
        }
        return 1;
    }

    public int setReplyTime(int id, String replytime){
        if(getStatus(id) != 1){
            return 3;
        }
        try{
            jdbcTemplate.update("UPDATE feedback SET replytime = ? WHERE id = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, replytime);
                            preparedStatement.setInt(2, id);
                        }
                    });
        }catch (Exception exception){
            return 2;
        }
        return 1;
    }
}
