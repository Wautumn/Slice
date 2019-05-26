package com.example.slice.entity;

import java.util.List;

public class ProjectTask {
    private int id;
    private String name;
    private String description;
    private String starttime;
    private String endtime;
    private int projectid;
    private List<Integer> userid;
    private int status;
    private String changetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public List<Integer> getUserid() {
        return userid;
    }

    public void setUserid(List<Integer> userid) {
        this.userid = userid;
    }

    public String getChangetime() {
        return changetime;
    }

    public void setChangetime(String changetime){
        this.changetime = changetime;
    }
}
