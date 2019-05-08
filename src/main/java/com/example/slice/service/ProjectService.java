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

    public List<Integer> findProjectByUser(int userid){return projectDAO.findProjectByUser(userid);}

    public List<Integer> findUserByProject(int projectid){return projectDAO.findUserByProject(projectid);}

    public Project findProjectByName(String name){return projectDAO.findProjectByName(name);}

    public Project findProjectById(int id){return projectDAO.findProjectById(id);}
}
