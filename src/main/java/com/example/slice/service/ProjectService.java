package com.example.slice.service;

import com.example.slice.dao.ProjectDAO;
import com.example.slice.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectDAO projectDAO;

    public int createProject(Project project, String[] members, String[] tasks) {
        return projectDAO.createProject(project, members, tasks);
    }

    public int addMembers(int projectid, String[] members){return projectDAO.addMembers(projectid, members);}

    public int addTasks(int projectid, String[] tasks){return projectDAO.addTasks(projectid, tasks);}

    public List<Integer> findAllTasks(int projectid){return projectDAO.findAllTasks(projectid);}

    public List<Project> findProjectByUser(int userid){return projectDAO.findProjectByUser(userid);}

    public List<String> findUserByProject(int projectid){return projectDAO.findUserByProject(projectid);}

    public List<Integer> findProjectByName(String name){return projectDAO.findProjectByName(name);}

    public Project findProjectById(int id){return projectDAO.findProjectById(id);}

    public List<Project> findProjectAttend(int userid){return projectDAO.findProjectAttend(userid);}

    public int setProjectName(int id, String name){
        return projectDAO.setProjectName(id, name);
    }

    public int setProjectDescription(int id, String description){
        return projectDAO.setProjectDescription(id, description);
    }

    public int setProjectStarttime(int id, String starttime){
        return projectDAO.setStarttime(id, starttime);
    }

    public int setProjectEndtime(int id, String endtime){
        return projectDAO.setEndtime(id, endtime);
    }
}
