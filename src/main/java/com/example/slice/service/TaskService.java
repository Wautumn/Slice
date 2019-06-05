package com.example.slice.service;

import com.example.slice.dao.TaskDAO;
import com.example.slice.entity.Task;
import com.example.slice.utility.ConvertDate;
import com.example.slice.utility.DateConvert;
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

    @Autowired
    DateConvert dateConvert;

    public Task findTaskById(int id) {
        Task task = taskDAO.findTaskById(id);
        return task;

    }

    public List<Task> findTaskByUserId(int userid) {
        List<Task> result = new LinkedList<>();
        LocalDate localDate = LocalDate.now();
        LocalDate now = LocalDate.now();
        List<Task> tasks = taskDAO.findTaskByUserid(userid);
//        for(Task task:tasks){
//            String starttime=task.getStarttime();
//            String endtime=task.getFinishtime();
//            LocalDate start=dateConvert.StringToLD(starttime);//开始的日期
//            LocalDate end=dateConvert.StringToLD(endtime);//结束的日期
//            if((now.isEqual(start)||now.isAfter(start))&&(now.equals(end)||now.isBefore(end))){
//                result.add(task);
//            }
//
//        }
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
        List<Task> result = new LinkedList<>();
        LocalDate now = LocalDate.now();
        for (Task task : tasks) {
            String starttime = task.getStarttime();
            String endtime = task.getFinishtime();
            if (starttime == null || endtime == null) {
                break;
            }
            LocalDate start = dateConvert.StringToLD(starttime);//开始的日期
            LocalDate end = dateConvert.StringToLD(endtime);//结束的日期
            System.out.println(start);
            System.out.println(end);
            System.out.println("now" + now);
            System.out.println(now.equals(start));
            System.out.println(now.equals(end));
            if ((now.isEqual(start) || now.isAfter(start)) && (now.equals(end) || now.isBefore(end))) {
                result.add(task);
            }

        }
        return result;
    }


    public void startTask(int id){
        taskDAO.startTask(id);
    }

    public void finishTask(int id,String time){
        Task task=new Task();
        task.setId(id);
        task.setRealfinish(time);
        taskDAO.finishTask(task);
    }

    public void breakTask(int id,String time){
        Task task=new Task();
        task.setId(id);
        task.setRealfinish(time);
        taskDAO.breakTask(task);
    }


    public void delayTask(int id){
        taskDAO.delayTask(id);

    }

}
