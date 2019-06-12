package com.example.slice.service;

import com.example.slice.dao.TaskDAO;
import com.example.slice.entity.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @Author: WHQ
 * @Date: 2019/4/15 20:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
    @Autowired
    TaskService taskService;

    @Autowired
    TaskDAO taskDAO;


    @Test
    public void insertTask(){
        Date date=new Date();
        Task task=new Task();
        task.setName("好好学习4");
        task.setUserid(1);
        task.setDescription("");
        task.setSettime(date.toString());
        task.setStatus(0);
        taskService.insertTask(task);
        int i=task.getId();
        System.out.println(i);
    }

    @Test
    public void getTaskByUserId(){
        List<Task> tasks=taskService.findTaskByUserId(1);
        for(int i =0;i<tasks.size();i++){
            System.out.println(tasks.get(i).getName());
        }
    }

    @Test
    public void deleteTask(){
       taskService.deleteTask(1);
    }


    @Test
    public void test(){
        taskService.startTask(41);
        Date date=new Date();
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        String now=sdf.format(date);
        taskService.finishTask(41,now);
        taskService.breakTask(42,now);
        taskService.delayTask(39);
    }

}
