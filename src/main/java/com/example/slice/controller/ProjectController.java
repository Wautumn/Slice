package com.example.slice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.slice.entity.Project;
import com.example.slice.service.ProjectService;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    ProjectService projectService;

    private String[] splitStrings(JSONArray members){
        String[] mem_names = new String[members.size()];
        for(int i = 0; i < members.size(); i++){
            mem_names[i] = members.get(i).toString();
        }

        return mem_names;
    }

    @RequestMapping(value = "/createProject", method = RequestMethod.POST)
    @ResponseBody
    public int createProject(@RequestBody JSONObject jsonObject){
        Project project = new Project();

        int userid = Integer.parseInt(jsonObject.get("userid").toString());
        String name = jsonObject.get("name").toString();
        String description = jsonObject.get("description").toString();
        String starttime = jsonObject.get("starttime").toString();
        String endtime = jsonObject.get("endtime").toString();

        JSONArray members = jsonObject.getJSONArray("members");
        String[] mem_names = splitStrings(members);

        JSONArray tasks = jsonObject.getJSONArray("subtasks");
        String[] task_names = splitStrings(tasks);

        project.setUserid(userid);
        project.setName(name);
        project.setDescription(description);
        project.setStarttime(starttime);
        project.setEndtime(endtime);

        return projectService.createProject(project, mem_names, task_names);
    }

    @RequestMapping(value = "/addMembers", method = RequestMethod.POST)
    @ResponseBody
    public int addMembers(@RequestBody JSONObject jsonObject){
        int projectid = jsonObject.getInteger("projectid");

        JSONArray member = jsonObject.getJSONArray("members");
        String[] mem_name = splitStrings(member);
        return projectService.addMembers(projectid, mem_name);
    }

    @RequestMapping(value ="/addSubTask", method = RequestMethod.POST)
    @ResponseBody
    public int addTasks(@RequestBody JSONObject jsonObject){
        int projectid = jsonObject.getInteger("projectid");

        JSONArray tasks = jsonObject.getJSONArray("subtasks");
        String[] task_name = splitStrings(tasks);
        return projectService.addTasks(projectid, task_name);
    }

    @RequestMapping(value = "/findAllSubTasks", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findAllTasks(int projectid){
        return projectService.findAllTasks(projectid);
    }

    @RequestMapping(value = "/findProjectByName", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findProjectByName(String name) {
        return projectService.findProjectByName(name);
    }

    @RequestMapping(value = "/findProjectById", method = RequestMethod.GET)
    @ResponseBody
    public Project findProjectById(int id){
        return projectService.findProjectById(id);
    }

    //查找用户创建的项目
    @RequestMapping(value="/getCreatedProject", method = RequestMethod.GET)
    @ResponseBody
    public List<Project> findProjectByUser(int userid){
        return projectService.findProjectByUser(userid);
    }

    //查找项目成员
    @RequestMapping(value="/getProjectUser", method = RequestMethod.GET)
    @ResponseBody
    public List<String> findUserByProject(int projectid){
        return projectService.findUserByProject(projectid);
    }

    //查找用户参与的项目（不含创建）
    @RequestMapping(value = "/getJoinedProject", method = RequestMethod.GET)
    @ResponseBody
    public List<Project> findProjectAttend(int userid){
        return projectService.findProjectAttend(userid);
    }

    @RequestMapping(value = "/setProjectName", method = RequestMethod.GET)
    @ResponseBody
    public int setProjectName(int id, String name){
        return projectService.setProjectName(id, name);
    }

    @RequestMapping(value = "/setProjectDescription", method = RequestMethod.GET)
    @ResponseBody
    public int setProjectDescription(int id, String description){
        return projectService.setProjectDescription(id, description);
    }

    @RequestMapping(value = "/setProjectStarttime", method = RequestMethod.GET)
    @ResponseBody
    public int setProjectStarttime(int id, String starttime){
        return projectService.setProjectStarttime(id, starttime);
    }

    @RequestMapping(value = "/setProjectEndtime", method = RequestMethod.GET)
    @ResponseBody
    public int setProjectEndtime(int id, String endtime){
        return projectService.setProjectEndtime(id, endtime);
    }
}
