package com.example.modulestudent;

import java.io.Serializable;

public class Major implements Serializable {
    private int resourceID;
    private String majorName;
    private String majorDescription;
    public Major(int resourceID, String majorName, String majorDescription) {
        this.resourceID = resourceID;
        this.majorName = majorName;
        this.majorDescription = majorDescription;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorDescription() {
        return majorDescription;
    }

    public void setMajorDescription(String majorDescription) {
        this.majorDescription = majorDescription;
    }
}
