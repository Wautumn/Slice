package com.example.slice.dao;

import com.example.slice.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: WHQ
 * @Date: 2019/4/15 19:52
 */
@Mapper
@Component
public interface TaskDAO {

    Task findTaskById(int id);

    List<Task> findTaskByUserid(int userid);

    int insertNewTask(Task task);

    void deleteTask(int taskid);
}
