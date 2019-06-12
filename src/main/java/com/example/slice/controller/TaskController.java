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
    public List<Task> getAllTasks(int userid) {
        List<Task> tasks = taskService.findTaskByUserId(userid);
        return tasks;

    }

    @RequestMapping(value = "/getTodayTasksByUserid", method = RequestMethod.GET)
    public List<Task> getTodayTasks(int userid) {
        List<Task> tasks = taskService.getTodayTask(userid);
        return tasks;
    }


    @RequestMapping(value = "/insertTask", method = RequestMethod.POST)
    public int insertTask(@RequestBody JSONObject jsonObject) {
        Task task = new Task();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int userid = Integer.parseInt(jsonObject.get("userid").toString());
        String name = jsonObject.get("name").toString();
        String description = jsonObject.get("description").toString();
        String starttime = jsonObject.get("starttime").toString();
        String finishtime = jsonObject.get("finishtime").toString();

        if (taskService.ifExistSameNameTask(userid, name) == 0) {
            return -1;//插入失败
        } else {
            int status = 1;
            task.setStarttime(starttime);
            task.setFinishtime(finishtime);
            task.setUserid(userid);
            task.setName(name);
            task.setDescription(description);
            task.setStatus(status);
            task.setSettime(sdf.format(date));

            taskService.insertTask(task);
            return task.getId();
        }

    }

    @RequestMapping(value = "/deleteTask", method = RequestMethod.DELETE)
    public String deleteTask(int id) {
        String response;
        Task task = taskService.findTaskById(id);
        if (task == null)
            response = "sorry,not exist this task";
        else {
            taskService.deleteTask(id);
            response = "delete success!";
        }
        return response;

    }

    @RequestMapping(value = "/changeTaskDescription", method = RequestMethod.POST)
    public String changeTaskDes(@RequestBody JSONObject jsonObject) {
        int id = Integer.parseInt(jsonObject.getString("id"));
        String des = jsonObject.getString("description");
        String response;
        Task task = taskService.findTaskById(id);
        if (task == null)
            response = "sorry,not exist this task";
        else {
            taskService.changeTaskDes(id, des);
            response = "change success!";
        }
        return response;

    }

    @RequestMapping(value = "/startTask", method = RequestMethod.GET)
    public void startTask(int id){
       taskService.startTask(id);
    }

    @RequestMapping(value = "/finishTask", method = RequestMethod.GET)
    public void finishTask(int id,String time){
      taskService.finishTask(id,time);
    }

    @RequestMapping(value = "/breakTask", method = RequestMethod.GET)
    public void breakTask(int id,String time){
       taskService.breakTask(id,time);
    }

    @RequestMapping(value = "/delayTask", method = RequestMethod.GET)
    public void delayTask(int id){
       taskService.delayTask(id);

    }
}
