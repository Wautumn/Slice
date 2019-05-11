package com.example.slice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.slice.dao.FeedbackDAO;
import com.example.slice.entity.FeedBack;
import com.example.slice.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    //Normal User
    @RequestMapping(value = "/createFeedback", method = RequestMethod.POST)
    @ResponseBody
    public int createFeedback(@RequestBody JSONObject jsonObject){
        String title = jsonObject.getString("title");
        String content = jsonObject.getString("content");
        int userid = jsonObject.getInteger("userid");
        String settime = jsonObject.getString("settime");

        FeedBack feedBack = new FeedBack();
        feedBack.setUserid(userid);
        feedBack.setSettime(settime);
        feedBack.setTitle(title);
        feedBack.setContent(content);

        return feedbackService.createFeedback(feedBack);
    }

    @RequestMapping(value = "/deleteFeedback", method = RequestMethod.GET)
    @ResponseBody
    public int deleteFeedback(int id){
        return feedbackService.deleteFeedback(id);
    }

    @RequestMapping(value = "/findFeedbackById", method = RequestMethod.GET)
    @ResponseBody
    public FeedBack findFeedbackById(int id){
        return feedbackService.findFeedbackById(id);
    }

    @RequestMapping(value = "/findFeedbackByUser", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findFeedbackByUser(int userid){
        return feedbackService.findFeedbackByUser(userid);
    }

    @RequestMapping(value = "/findFeedbackByTitle", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findFeedbackByTitle(int userid, String title){
        return feedbackService.findFeedBackByUserTitle(userid, title);
    }

    @RequestMapping(value = "/findFeedbackBySettime", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findFeedbackBySettime(int userid, String settime){
        return feedbackService.findFeedbackByUserTime(userid, settime);
    }

    @RequestMapping(value = "/findFeedbackByStatus", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findFeedbackByStatus(int userid, int status){
        return feedbackService.findFeedbackByUserStatus(userid, status);
    }

    @RequestMapping(value = "/setFeedbackTitle", method = RequestMethod.GET)
    @ResponseBody
    public int setTitle(int id, String title){
        return feedbackService.setFeedbackTitle(id, title);
    }

    @RequestMapping(value = "/setFeedbackContent", method = RequestMethod.GET)
    @ResponseBody
    public int setContent(int id, String content){
        return feedbackService.setFeedbackContent(id, content);
    }

    //Administrator
    @RequestMapping(value = "/findAllFeedbackAdmin", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedBack> findAllFeedback(){
        return feedbackService.findFeedback();
    }

    @RequestMapping(value = "/findFeedbackByStatusAdmin", method = RequestMethod.GET)
    @ResponseBody
    public List<FeedBack> findFeedbackStatus(int status){
        return feedbackService.findFeedbackByStatus(status);
    }

    @RequestMapping(value = "/replyFeedback", method = RequestMethod.POST)
    @ResponseBody
    public int replyFeedback(@RequestBody JSONObject jsonObject){
        int id = jsonObject.getInteger("id");
        String answer = jsonObject.getString("answer");
        String replytime = jsonObject.getString("replytime");

        int real_status = feedbackService.getStatus(id);
        if(real_status == 2){
            return 3;
        }

        int result_a = feedbackService.setFeedbackAnswer(id, answer);
        if(result_a != 1){
            return 2;
        }
        int result_r = feedbackService.setFeedbackReplytime(id, replytime);
        if(result_r != 1){
            return 2;
        }

        int result_s = feedbackService.setFeedbackStatus(id, 2);
        if(result_s != 1){
            return 2;
        }

        return 1;
    }
}
