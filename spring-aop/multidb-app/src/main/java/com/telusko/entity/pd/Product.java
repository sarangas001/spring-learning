package com.telusko.entity.pd;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    private Long pid;
    private String pname;
    private Double pcost;


    public Product() {
    }

    public Product(Long pid, String pname, Double pcost) {
        this.pid = pid;
        this.pname = pname;
        this.pcost = pcost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", pcost=" + pcost +
                '}';
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Double getPcost() {
        return pcost;
    }

    public void setPcost(Double pcost) {
        this.pcost = pcost;
    }
}
