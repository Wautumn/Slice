package com.example.slice.service;

import com.example.slice.dao.TaskDAO;
import com.example.slice.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: WHQ
 * @Date: 2019/4/15 19:54
 */
@Service
public class TaskService {
    @Autowired
    TaskDAO taskDAO;

    public Task findTaskById(int id){
        Task task=taskDAO.findTaskById(id);
        return task;

    }

    public List<Task> findTaskByUserId(int userid){
        List<Task> tasks=taskDAO.findTaskByUserid(userid);
        return tasks;
    }

    public int insertTask(Task task){
        int insertid=taskDAO.insertNewTask(task);
        return insertid;
    }

    public void deleteTask(int id){
        taskDAO.deleteTask(id);
    }

}
