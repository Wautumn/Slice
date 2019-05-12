package com.example.slice.service;

import com.example.slice.dao.FeedbackDAO;
import com.example.slice.entity.FeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    FeedbackDAO feedbackDAO;

    //Normal User
    public int createFeedback(FeedBack feedBack){
        return feedbackDAO.createFeedback(feedBack);
    }

    public int deleteFeedback(int id){
        return feedbackDAO.deleteFeedback(id);
    }

    public FeedBack findFeedbackById(int id){
        return feedbackDAO.findFeedbackById(id);
    }

    public List<FeedBack> findFeedbackByUser(int userid){
        return feedbackDAO.findFeedbackByUser(userid);
    }

    public List<Integer> findFeedBackByUserTitle(int userid, String title){
        return feedbackDAO.findFeedbackByTitle(userid, title);
    }

    public List<Integer> findFeedbackByUserTime(int userid, String settime){
        return feedbackDAO.findFeedbackBySettime(userid, settime);
    }

    public List<Integer> findFeedbackByUserStatus(int userid, int status){
        return feedbackDAO.findFeedbackByStatus(userid, status);
    }

    public int setFeedbackTitle(int id, String title){
        return feedbackDAO.setTitle(id, title);
    }

    public int setFeedbackContent(int id, String content){
        return feedbackDAO.setContent(id, content);
    }

    //Administrator
    public List<FeedBack> findFeedback(){
        return feedbackDAO.findAllFeedback();
    }

    public List<FeedBack> findFeedbackByStatus(int status){
        return feedbackDAO.findFeedbackByStatus(status);
    }

    public int setFeedbackAnswer(int id, String answer){
        return feedbackDAO.setAnswer(id, answer);
    }

    public int setFeedbackReplytime(int id, String replytime){
        return feedbackDAO.setReplyTime(id, replytime);
    }

    public int setFeedbackStatus(int id, int status){
        return feedbackDAO.setStatus(id, status);
    }

    public int getStatus(int id){
        return feedbackDAO.getStatus(id);
    }
}
