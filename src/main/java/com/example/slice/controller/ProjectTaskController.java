package com.example.slice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.slice.entity.ProjectTask;
import com.example.slice.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectTaskController {
    @Autowired
    ProjectTaskService projectTaskService;

    @RequestMapping(value = "/addSubTasks", method = RequestMethod.POST)
    @ResponseBody
    public int createProjectTask(@RequestBody JSONObject jsonObject) {
        ProjectTask projectTask = new ProjectTask();

        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        String starttime = jsonObject.getString("starttime");
        String endtime = jsonObject.getString("endtime");
        int projectid = jsonObject.getInteger("projectid");


        projectTask.setName(name);
        projectTask.setDescription(description);
        projectTask.setStarttime(starttime);
        projectTask.setEndtime(endtime);
        projectTask.setProjectid(projectid);

        return projectTaskService.createProjectTask(projectTask);
    }

    @RequestMapping(value = "/deleteProjectTask", method = RequestMethod.GET)
    @ResponseBody
    public int deleteProjectTask(int id) {
        return projectTaskService.deleteProjectTask(id);
    }

    @RequestMapping(value = "/findProjectTaskId", method = RequestMethod.GET)
    @ResponseBody
    public int findProjectTaskId(int projectid, String name) {
        return projectTaskService.findProjectTaskId(projectid, name);
    }

    @RequestMapping(value = "/findProjectTaskByUser", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findTasksByUser(int userid, int projectid) {
        return projectTaskService.findTasksByUser(userid, projectid);
    }

    @RequestMapping(value = "/findProjectTaskById", method = RequestMethod.GET)
    @ResponseBody
    public ProjectTask findTaskById(int id) {
        return projectTaskService.findTasksById(id);
    }

    @RequestMapping(value = "/findProjectTaskByName", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findTaskByName(String name, int projectid) {
        return projectTaskService.findTasksByName(projectid, name);
    }

    @RequestMapping(value = "/setProjectTask", method = RequestMethod.POST)
    @ResponseBody
    public int setTask(@RequestBody JSONObject jsonObject) {
        int id = jsonObject.getInteger("id");
        String description = jsonObject.getString("description");
        String starttime = jsonObject.getString("starttime");
        String endtime = jsonObject.getString("endtime");
        int userid = jsonObject.getInteger("userid");

        return projectTaskService.setTask(id, description, starttime, endtime, userid);
    }

    @RequestMapping(value = "/setTaskName", method = RequestMethod.GET)
    @ResponseBody
    public int setTaskName(int id, String name) {
        return projectTaskService.setTaskName(id, name);
    }

    @RequestMapping(value = "/setTaskDescription", method = RequestMethod.GET)
    @ResponseBody
    public int setTaskDescription(int id, String description) {
        return projectTaskService.setTaskDescription(id, description);
    }

    @RequestMapping(value = "/setTaskStarttime", method = RequestMethod.GET)
    @ResponseBody
    public int setTaskStarttime(int id, String starttime) {
        return projectTaskService.setTaskStarttime(id, starttime);
    }

    @RequestMapping(value = "/setTaskEndtime", method = RequestMethod.GET)
    @ResponseBody
    public int setTaskEndtime(int id, String endtime) {
        return projectTaskService.setTaskEndtime(id, endtime);
    }

    @RequestMapping(value = "/distributeTask", method = RequestMethod.GET)
    @ResponseBody
    public int setTaskUserid(int projectid, int taskid, String username) {
        return projectTaskService.setTaskUserid(projectid, taskid, username);
    }

    @RequestMapping(value = "/getDistributeTask", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getDistributeTask(int projectid) {
        return projectTaskService.getDistributeTask(projectid);
    }

    @RequestMapping(value = "/getTodaysTasks", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectTask> getTodaysTasks(int userid) {
        return projectTaskService.getTodaysTasks(userid);
    }

    @RequestMapping(value = "/getAllTasks", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectTask> getAllProjectTask(int userid){
        return projectTaskService.getAllProjectTask(userid);
    }

    @RequestMapping(value = "/startTask", method = RequestMethod.GET)
    @ResponseBody
    public int startTask(int taskid){
        return projectTaskService.startTask(taskid);
    }

    @RequestMapping(value = "/finishTask", method = RequestMethod.GET)
    @ResponseBody
    public int finishTask(int taskid, String time){
        return projectTaskService.finishTask(taskid, time);
    }

    @RequestMapping(value = "/discardTask", method = RequestMethod.GET)
    @ResponseBody
    public int discardTask(int taskid, String time){
        return projectTaskService.discardTask(taskid, time);
    }

    @RequestMapping(value = "/delayTask", method = RequestMethod.GET)
    @ResponseBody
    public int delayTask(int taskid){
        return projectTaskService.delayTask(taskid);
    }

    @RequestMapping(value = "/getAnalyzeData", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, HashMap<String, Integer>> getData(int userid, String start, String end){
        return projectTaskService.getData(userid, start, end);
    }
}
