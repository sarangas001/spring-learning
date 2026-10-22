package com.telusko.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String courseName;
    private String trainerName;
    private Integer duration;
    private Double fees;

    // constructor
    public Course() {
    }

    public Course(Integer id, String courseName, String trainerName, Integer duration, Double fees) {
        this.id = id;
        this.courseName = courseName;
        this.trainerName = trainerName;
        this.duration = duration;
        this.fees = fees;
    }

    // toString
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", trainerName='" + trainerName + '\'' +
                ", duration=" + duration +
                ", fees=" + fees +
                '}';
    }

    // == setters and getters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }
}
