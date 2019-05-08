package com.example.slice.entity;

public class Project {
    private int id;

    private String name;

    private String description;

    private int userid;

    private String starttime;

    private String endtime;

    private int status;

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}

    public int getUserid(){return userid;}
    public void setUserid(int userid){this.userid = userid;}

    public String getStarttime(){return starttime;}
    public void setStarttime(String starttime){this.starttime = starttime;}

    public String getEndtime(){return endtime;}
    public void setEndtime(String endtime){this.endtime = endtime;}

    public int getStatus(){return status;}
    public void setStatus(int status){this.status = status;}
}
