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

import java.util.List;

@Controller
public class ProjectTaskController {
    @Autowired
    ProjectTaskService projectTaskService;

    @RequestMapping(value = "/createProjectTask", method = RequestMethod.POST)
    @ResponseBody
    public int createProjectTask(@RequestBody JSONObject jsonObject){
        ProjectTask projectTask = new ProjectTask();

        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        String starttime = jsonObject.getString("starttime");
        String endtime = jsonObject.getString("endtime");
        int projectid = jsonObject.getInteger("projectid");
        int userid = jsonObject.getInteger("userid");

        projectTask.setName(name);
        projectTask.setDescription(description);
        projectTask.setStarttime(starttime);
        projectTask.setEndtime(endtime);
        projectTask.setProjectid(projectid);
        projectTask.setUserid(userid);

        return projectTaskService.createProjectTask(projectTask);
    }

    @RequestMapping(value = "/deleteProjectTask", method = RequestMethod.GET)
    @ResponseBody
    public int deleteProjectTask(int id){
        return projectTaskService.deleteProjectTask(id);
    }

    @RequestMapping(value = "/findProjectTaskId", method = RequestMethod.GET)
    @ResponseBody
    public int findProjectTaskId(int projectid, String name){
        return projectTaskService.findProjectTaskId(projectid, name);
    }

    @RequestMapping(value = "/findProjectTaskByUser", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findTasksByUser(int userid, int projectid) {
        return projectTaskService.findTasksByUser(userid, projectid);
    }

    @RequestMapping(value = "/findProjectTaskById", method = RequestMethod.GET)
    @ResponseBody
    public ProjectTask findTaskById(int id){
        return projectTaskService.findTasksById(id);
    }

    @RequestMapping(value = "/findProjectTaskByName", method = RequestMethod.GET)
    @ResponseBody
    public ProjectTask findTaskByName(String name, int projectid){
        return projectTaskService.findTasksByName(projectid, name);
    }

    @RequestMapping(value = "/setProjectTask", method = RequestMethod.POST)
    @ResponseBody
    public int setTask(@RequestBody JSONObject jsonObject){
        int id = jsonObject.getInteger("id");
        String description = jsonObject.getString("description");
        String starttime = jsonObject.getString("starttime");
        String endtime = jsonObject.getString("endtime");
        int userid = jsonObject.getInteger("userid");

        return projectTaskService.setTask(id, description, starttime, endtime, userid);
    }

    @RequestMapping(value = "/setTaskName", method = RequestMethod.GET)
    @ResponseBody
    public int setTaskName(int id, String name){
        return projectTaskService.setTaskName(id, name);
    }

    @RequestMapping(value = "/setTaskDescription", method = RequestMethod.GET)
    @ResponseBody
    public int setTaskDescription(int id, String description){
        return projectTaskService.setTaskDescription(id, description);
    }

    @RequestMapping(value = "/setTaskStarttime", method = RequestMethod.GET)
    @ResponseBody
    public int setTaskStarttime(int id, String starttime){
        return projectTaskService.setTaskStarttime(id, starttime);
    }

    @RequestMapping(value = "/setTaskEndtime", method = RequestMethod.GET)
    @ResponseBody
    public int setTaskEndtime(int id, String endtime){
        return projectTaskService.setTaskEndtime(id, endtime);
    }

    @RequestMapping(value = "/setTaskUserid", method = RequestMethod.GET)
    @ResponseBody
    public int setTaskUserid(int id, int userid){
        return projectTaskService.setTaskUserid(id, userid);
    }
}
