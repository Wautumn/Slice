package com.example.slice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.slice.entity.Summary;
import com.example.slice.service.SummaryService;
import com.example.slice.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SummaryController {
    @Autowired
    private SummaryService summaryService;

    @RequestMapping(value = "/writeSummary", method = RequestMethod.POST)
    @ResponseBody
    public int createSummary(@RequestBody JSONObject jsonObject){
        int userid = jsonObject.getInteger("userid");
        int score = jsonObject.getInteger("score");
        String content = jsonObject.getString("content");

        String date = DateUtil.getCurrentDate();

        Summary summary = new Summary();
        summary.setUserid(userid);
        summary.setDate(date);
        summary.setContent(content);
        summary.setScore(score);
        return summaryService.createSummary(summary);
    }

    @RequestMapping(value = "/deleteSummary", method = RequestMethod.GET)
    @ResponseBody
    public int deleteSummary(int id){
        return summaryService.deleteSummary(id);
    }

    @RequestMapping(value = "/changeSummary", method = RequestMethod.POST)
    @ResponseBody
    public int changeSummary(@RequestBody JSONObject jsonObject){
        int id = jsonObject.getInteger("id");
        String content = jsonObject.getString("content");
        int score = jsonObject.getInteger("score");

        return summaryService.changeSummary(id, content, score);
    }

    @RequestMapping(value = "/setSummaryContent", method = RequestMethod.GET)
    @ResponseBody
    public int setContent(int id, String content){
        return summaryService.setContent(id, content);
    }

    @RequestMapping(value = "/setSummaryScore", method = RequestMethod.GET)
    @ResponseBody
    public int setScore(int id, int score){
        return summaryService.setScore(id, score);
    }

    @RequestMapping(value = "/findSummaryById", method = RequestMethod.GET)
    @ResponseBody
    public Summary findSummaryById(int id){
        return summaryService.findSummary(id);
    }

    @RequestMapping(value = "/findSummaryByUserDate", method = RequestMethod.GET)
    @ResponseBody
    public Summary findSummaryByUserDate(int userid, String date){
        return summaryService.findSummary(userid, date);
    }

    @RequestMapping(value = "/findSummaryByUser", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findSummaryByUser(int userid){
        return summaryService.findSummaryByUser(userid);
    }

    @RequestMapping(value = "/getTodaysSummary", method = RequestMethod.GET)
    @ResponseBody
    public Summary findSummaryToday(int userid){
        return summaryService.findSummaryToday(userid);
    }
}
