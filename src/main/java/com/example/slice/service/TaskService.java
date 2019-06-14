package com.example.slice.service;

import com.example.slice.dao.TaskDAO;
import com.example.slice.entity.Task;
import com.example.slice.utility.ConvertDate;
import com.example.slice.utility.DateConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.HashMap;
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
            System.out.println(task.getName());
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


    public void startTask(int id) {
        taskDAO.startTask(id);
    }

    public void finishTask(int id, String time) {
        Task task = new Task();
        task.setId(id);
        task.setRealfinish(time);
        taskDAO.finishTask(task);
    }

    public void breakTask(int id, String time) {
        Task task = new Task();
        task.setId(id);
        task.setRealfinish(time);
        taskDAO.breakTask(task);
    }


    public void delayTask(int id) {
        taskDAO.delayTask(id);

    }


    public List getAnaly(int userid) {
        List<Task> tasks = taskDAO.findTaskByUserid(userid);
      for(int i=0;i<tasks.size();i++){
          System.out.println(tasks.get(i).getId());
          System.out.println(tasks.get(i).getRealfinish());
          if(tasks.get(i).getRealfinish()!=null)System.out.println(dateConvert.StringToLD(tasks.get(i).getRealfinish()));

      }
        LocalDate now = LocalDate.now();//现在日期
        LocalDate before = now.minusWeeks(1);//前一周的日期

        int arr[] = new int[7];//已完成
        for (int i = 0; i < 7; i++) {
            arr[i] = 0;
        }
        int arr2[] = new int[7];//已中断
        for (int i = 0; i < 7; i++) {
            arr2[i] = 0;
        }
        int arr3[] = new int[7];//已过期
        for (int i = 0; i < 7; i++) {
            arr3[i] = 0;
        }
        List<HashMap<String, Object>> result = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate current = before.plusDays(i);
            System.out.println(current);
            for (Task task : tasks) {
                if (task.getRealfinish() == null) {
                    arr3[i]++;
                } else {
                    if (dateConvert.StringToLD(task.getRealfinish()).equals( current) ){
                        if (task.getStatus() == 3) {
                            arr[i]++;
                        }
                        if (task.getStatus() == 4)
                            arr2[i]++;
                        if (task.getStatus() == 5) {
                            arr3[i]++;
                        }
                    }
                }
            }

            HashMap<String, Object> oneday = new HashMap<>();
            oneday.put("weekday", current);
            oneday.put("已完成", arr[i]);
            oneday.put("中断", arr2[i]);
            oneday.put("过期", arr3[i]);

            result.add(oneday);

        }

        return result;


    }

}
