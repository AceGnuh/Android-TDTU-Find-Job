package com.example.modulestudent;

import java.io.Serializable;
import java.util.List;

public class HightlightJobs implements Serializable {

    private String id;
    private int resourceID;
    private String jobName;
    private String companyName;
    private String address;
    private String salary;
    private String date;
    private String major;
    private String description;
    private List<String> students;
    private String img;

    public HightlightJobs(){

    }

    public HightlightJobs(int resourceID, String jobName, String companyName, String address, String salary, String date) {
        this.resourceID = resourceID;
        this.jobName = jobName;
        this.companyName = companyName;
        this.address = address;
        this.salary = salary;
        this.date = date;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
