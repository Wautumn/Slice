package com.example.slice.dao;

import com.example.slice.entity.Summary;
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
public class SummaryDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int createSummary(Summary summary){
        try{
            String sql = "INSERT INTO summary(userid, score, date, content) VALUES(?, ?, ?, ?)";
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, summary.getUserid());
                    ps.setInt(2, summary.getScore());
                    ps.setString(3, summary.getDate());
                    ps.setString(4, summary.getContent());
                    return ps;
                }
            }, holder);
            int id = holder.getKey().intValue();
            return id;
        }catch (Exception exception) {
            return -1;
        }
    }

    public int deleteSummary(int id){
        try {
            jdbcTemplate.update("DELETE FROM summary WHERE id = ?",
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

    public int setContent(int id, String content){
        try{
            jdbcTemplate.update("UPDATE summary SET content = ? WHERE id = ?",
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

    public int setScore(int id, int score){
        try{
            jdbcTemplate.update("UPDATE summary SET score = ? WHERE id = ?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, score);
                            preparedStatement.setInt(2, id);
                        }
                    });
        }catch (Exception exception){
            return 2;
        }
        return 1;
    }

    public Summary findSummary(int id){
        try{
            Object[] params = new Object[]{id};
            String sql = "SELECT * FROM summary WHERE id = ?";
            Summary summary = jdbcTemplate.queryForObject(sql, params, new RowMapper<Summary>() {
                @Override
                public Summary mapRow(ResultSet resultSet, int i) throws SQLException {
                    Summary summary = new Summary();
                    summary.setId(resultSet.getInt("id"));
                    summary.setDate(resultSet.getString("date"));
                    summary.setUserid(resultSet.getInt("userid"));
                    summary.setScore(resultSet.getInt("score"));
                    summary.setContent(resultSet.getString("content"));
                    return summary;
                }
            });
            return summary;
        }catch (Exception exception){
            return null;
        }
    }

    public Summary findSummary(int userid, String date){
        try{
            Object[] params = new Object[]{userid, date};
            String sql = "SELECT * FROM summary WHERE userid = ? AND date = ?";
            Summary summary = jdbcTemplate.queryForObject(sql, params, new RowMapper<Summary>() {
                @Override
                public Summary mapRow(ResultSet resultSet, int i) throws SQLException {
                    Summary summary = new Summary();
                    summary.setId(resultSet.getInt("id"));
                    summary.setDate(resultSet.getString("date"));
                    summary.setUserid(resultSet.getInt("userid"));
                    summary.setScore(resultSet.getInt("score"));
                    summary.setContent(resultSet.getString("content"));
                    return summary;
                }
            });
            return summary;
        }catch (Exception exception){
            return null;
        }
    }

    public List<Integer> findSummaryByUser(int userid){
        try{
            Object[] params = new Object[]{userid};
            String sql = "SELECT id FROM summary WHERE userid = ?";
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
}
