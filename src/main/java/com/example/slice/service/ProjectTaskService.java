package com.example.slice.service;

import com.example.slice.dao.ProjectTaskDAO;
import com.example.slice.entity.ProjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private ProjectTaskDAO projectTaskDAO;

    public int createProjectTask(ProjectTask projectTask){
        return projectTaskDAO.createProjectTask(projectTask);
    }

    public int deleteProjectTask(int id){
        return projectTaskDAO.deleteProjectTask(id);
    }

    public int findProjectTaskId(int projectid, String name){
        return projectTaskDAO.findProjectTaskId(projectid, name);
    }

    public List<Integer> findTasksByUser(int userid, int projectid){return projectTaskDAO.findTasksByUser(userid, projectid);}

    public ProjectTask findTasksById(int id){return projectTaskDAO.findTasksById(id);}

    public List<Integer> findTasksByName(int projectid, String name){return projectTaskDAO.findTasksByName(name, projectid);}

    public int setTask(int id, String description, String starttime, String endtime, int userid){
        return projectTaskDAO.setTask(id, description, starttime, endtime, userid);
    }

    public int setTaskName(int id, String name){
        return projectTaskDAO.setTaskName(id, name);
    }

    public int setTaskDescription(int id, String description){
        return projectTaskDAO.setTaskDescription(id, description);
    }

    public int setTaskStarttime(int id, String starttime){
        return projectTaskDAO.setStarttime(id, starttime);
    }

    public int setTaskEndtime(int id, String endtime){
        return projectTaskDAO.setEndtime(id, endtime);
    }

    public int setTaskUserid(int id, int userid){
        return projectTaskDAO.setTaskUserid(id, userid);
    }
}
