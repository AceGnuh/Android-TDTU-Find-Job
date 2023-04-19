package com.example.modulestudent;

import java.util.List;

public class Job {

    public Job(){}
    public Job(int resourceID, String jobName, String companyName, String address, String salary, String date) {
        this.resourceID = resourceID;
        this.jobName = jobName;
        this.companyName = companyName;
        this.address = address;
        this.salary = salary;
        this.date = date;
    }

    private int resourceID;
    private String id;
    private String jobName;
    private String companyName;
    private String address;
    private String salary;
    private String date;
    private String major;
    private String description;
    private List<String> students;

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

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
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

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Job{" +
                "resourceID=" + resourceID +
                ", id='" + id + '\'' +
                ", jobName='" + jobName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", salary='" + salary + '\'' +
                ", date='" + date + '\'' +
                ", major='" + major + '\'' +
                ", description='" + description + '\'' +
                ", students=" + students +
                '}';
    }
}

