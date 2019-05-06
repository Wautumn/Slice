package com.example.slice.service;

import com.example.slice.dao.ProjectDAO;
import com.example.slice.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectDAO projectDAO;

    public int createProject(Project project, String[] members){return projectDAO.createProject(project, members);}

    public int addMembers(int projectid, String[] members){return projectDAO.addMembers(projectid, members);}
}
