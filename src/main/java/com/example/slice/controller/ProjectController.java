package com.example.slice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.slice.entity.Project;
import com.example.slice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/createProject", method = RequestMethod.POST)
    @ResponseBody
    public int createProject(@RequestBody JSONObject jsonObject){
        Project project = new Project();

        //int userid, String[] members, String name, String description,
        //                             String starttime, String endtime
        int userid = Integer.parseInt(jsonObject.get("userid").toString());
        String name = jsonObject.get("name").toString();
        String description = jsonObject.get("description").toString();
        String starttime = jsonObject.get("starttime").toString();
        String endtime = jsonObject.get("endtime").toString();

        JSONArray members = jsonObject.getJSONArray("members");
        String[] mem_names = new String[members.size()];
        for(int i = 0; i < members.size(); i++){
            mem_names[i] = members.get(i).toString();
        }

        project.setUserid(userid);
        project.setName(name);
        project.setDescription(description);
        project.setStarttime(starttime);
        project.setEndtime(endtime);

        return projectService.createProject(project, mem_names);
    }

    @RequestMapping(value = "/addMembers", method = RequestMethod.POST)
    @ResponseBody
    public int addMembers(@RequestBody JSONObject jsonObject){
        int projectid = jsonObject.getInteger("projectid");

        JSONArray member = jsonObject.getJSONArray("members");
        String[] mem_name = new String[member.size()];
        for(int i = 0; i < member.size(); i++){
            mem_name[i] = member.get(i).toString();
        }

        return projectService.addMembers(projectid, mem_name);
    }
}
