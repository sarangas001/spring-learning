package com.telusko.service;

public class DevOps implements ICourse {

    public DevOps() {
        System.out.println("DevOps bean created");
    }

    @Override
    public boolean buyTheCourse(double price) {
        System.out.println("Registred in DevOps Course of Telusko and fees paid is " + price);
        return true;
    }
}
