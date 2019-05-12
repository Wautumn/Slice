package com.example.slice.service;

import com.example.slice.dao.SummaryDAO;
import com.example.slice.entity.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryService {
    @Autowired
    private SummaryDAO summaryDAO;

    public int createSummary(Summary summary){
        return summaryDAO.createSummary(summary);
    }

    public int deleteSummary(int id){
        return summaryDAO.deleteSummary(id);
    }

    public int setContent(int id, String content){
        return summaryDAO.setContent(id, content);
    }

    public int setScore(int id, int score){
        return summaryDAO.setScore(id, score);
    }

    public Summary findSummary(int id){
        return summaryDAO.findSummary(id);
    }

    public Summary findSummary(int userid, String date){
        return summaryDAO.findSummary(userid, date);
    }

    public List<Integer> findSummaryByUser(int userid){
        return summaryDAO.findSummaryByUser(userid);
    }

    public int changeSummary(int id, String content, int score){
        return summaryDAO.changeSummary(id, content, score);
    }
}
