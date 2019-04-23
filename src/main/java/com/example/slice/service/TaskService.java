package com.example.slice.service;

import com.example.slice.dao.TaskDAO;
import com.example.slice.entity.Task;
import com.example.slice.other.ConvertDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: WHQ
 * @Date: 2019/4/15 19:54
 */
@Service
public class TaskService {
    @Autowired
    TaskDAO taskDAO;

    @Autowired
    ConvertDate convertDate;

    public Task findTaskById(int id) {
        Task task = taskDAO.findTaskById(id);
        return task;

    }

    public List<Task> findTaskByUserId(int userid) {
        List<Task> tasks = taskDAO.findTaskByUserid(userid);
        return tasks;
    }

    public int insertTask(Task task) {
        int insertid = taskDAO.insertNewTask(task);
        return insertid;
    }

    public void deleteTask(int id) {
        taskDAO.deleteTask(id);
    }

    public void changeTaskDes(int id, String des) {
        taskDAO.changeTaskDes(id, des);
    }

    public int ifExistSameNameTask(int userid, String name) {
        List<Task> tasks = taskDAO.findTaskByUserid(userid);
        int flag = 0;
        for (Task task : tasks) {
            if (task.getName().equals(name)) {
                flag = 1;
                break;
            }
        }
        if (flag == 1) return 0;//存在同名任务
        else
            return 1;

    }

    /*
    返回今天的任务列表
     */
    public List<Task> getTodayTask(int userid) {
        List<Task> tasks = taskDAO.findTaskByUserid(userid);
        List<Task> usefulTasks = new LinkedList<>();
        LocalDate localDate = LocalDate.now();
        for (Task task : tasks) {
            String settime = task.getSettime();
            String deadline = task.getDeadline();
            LocalDate setdate = convertDate.StringToLocalDate(settime);
            LocalDate deaddate = convertDate.StringToLocalDate(deadline);
            if (deaddate.isAfter(localDate) || deaddate.equals(localDate)) {
                usefulTasks.add(task);
            }

        }
        return usefulTasks;
    }

}
