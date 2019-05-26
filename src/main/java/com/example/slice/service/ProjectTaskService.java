package com.example.slice.service;

import com.example.slice.dao.ProjectTaskDAO;
import com.example.slice.entity.ProjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectTaskService {
    @Autowired
    private ProjectTaskDAO projectTaskDAO;

    public int createProjectTask(ProjectTask projectTask, String[] usernames){
        return projectTaskDAO.createProjectTask(projectTask, usernames);
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

    public int setTask(int id, String description, String starttime, String endtime, String[] userid){
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

    public int setTaskUserid(int projectid, int taskid, String[] username){
        return projectTaskDAO.setTaskUserid(projectid, taskid, username);
    }

    public List<Map<String, Object>> getDistributeTask(int projectid){
        return projectTaskDAO.getDistributeTask(projectid);
    }

    public List<ProjectTask> getTodaysTasks(int userid){
        return projectTaskDAO.getTodaysTasks(userid);
    }

    public List<ProjectTask> getAllProjectTask(int userid){
        return projectTaskDAO.getAllProjectTask(userid);
    }

    public int startTask(int taskid){
        return projectTaskDAO.startTask(taskid);
    }

    public int finishTask(int taskid, String time){
        return projectTaskDAO.finishTask(taskid, time);
    }

    public int discardTask(int taskid, String time){
        return projectTaskDAO.discardTask(taskid, time);
    }

    public int delayTask(int taskid){
        return projectTaskDAO.delayTask(taskid);
    }

    public HashMap<String, HashMap<String, Integer>> getData(int userid, String s, String e){
        return projectTaskDAO.getData(userid, s, e);
    }

    public int delayProjectTask(int taskid, String endtime){
        return projectTaskDAO.delayProjectTask(taskid, endtime);
    }

    public int setPreProjecttask(int projectid, int taskid, int preid){
        return projectTaskDAO.setPreProjecttask(projectid, taskid, preid);
    }

    public List<ProjectTask> findPreTask(int taskid){
        return projectTaskDAO.findPreTask(taskid);
    }

    public List<ProjectTask> findPostTask(int taskid){
        return projectTaskDAO.findPostTask(taskid);
    }

    public Map<Integer, Map<String, Integer>> getProjectProgress(int projectid){
        return projectTaskDAO.getProjectProgress(projectid);
    }
}
