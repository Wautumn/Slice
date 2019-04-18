package com.example.slice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.slice.entity.Task;
import com.example.slice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: WHQ
 * @Date: 2019/4/15 19:54
 */
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;


    @RequestMapping(value = "/getTasksByUserid", method = RequestMethod.GET)
    public List<Task> insertTask(int userid) {
        List<Task> tasks = taskService.findTaskByUserId(userid);
        return tasks;

    }


    @RequestMapping(value = "/insertTask", method = RequestMethod.POST)
    public int insertTask(@RequestBody JSONObject jsonObject) {
        Task task = new Task();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        int userid = Integer.parseInt(jsonObject.get("userid").toString());
        String name = jsonObject.get("name").toString();
        int continuance = Integer.parseInt(jsonObject.get("continuance").toString());
        String description = jsonObject.get("description").toString();
        int status = 0;
        task.setUserid(userid);
        task.setName(name);
        task.setDescription(description);
        task.setContinuance(continuance);
        task.setStatus(status);
        task.setSettime(sdf.format(date));

        taskService.insertTask(task);
        return task.getId();

    }

    @RequestMapping(value = "/deleteTask", method = RequestMethod.DELETE)
    public String deleteTask(int id) {
        String response;
        Task task = taskService.findTaskById(id);
        if(task==null)
            response="sorry,not exist this task";
        else {
            taskService.deleteTask(id);
            response="delete success!";
        }
        return response;

    }

    @RequestMapping(value = "/changeTaskName", method = RequestMethod.GET)
    public String changeTaskName(int id,String name) {
        String response;
        Task task = taskService.findTaskById(id);
        if(task==null)
            response="sorry,not exist this task";
        else {
            taskService.changeTask(id,name);
            response="change success!";
        }
        return response;

    }
}
