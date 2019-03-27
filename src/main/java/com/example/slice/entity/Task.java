package com.example.slice.entity;

/**
 * @Author: WHQ
 * @Date: 2019/3/27 19:32
 */
public class Task {

    private int id;

    private int userid;

    private String name;

    private String description;

    private String settime;//设置时间

    private int status;

    private int continuance;//持续天数，可选

    private String starttime;

    private String finishtime;

    private int duration;//分钟为单位

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public String getSettime() {
        return settime;
    }

    public void setSettime(String settime) {
        this.settime = settime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getContinuance() {
        return continuance;
    }

    public void setContinuance(int continuance) {
        this.continuance = continuance;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
