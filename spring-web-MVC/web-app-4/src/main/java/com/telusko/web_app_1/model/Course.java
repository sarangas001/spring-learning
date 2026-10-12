package com.telusko.web_app_1.model;


public class Course
{
    private Integer cid;
    private String cname;
    private Double cost;

    public Course() {
    }



    public Course(Integer cid, String cname, Double cost) {
        this.cid = cid;
        this.cname = cname;
        this.cost = cost;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
