package com.example.modulestudent;

import java.io.Serializable;

public class Banner implements Serializable {
    private int resourceID;

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public Banner(int resourceID) {
        this.resourceID = resourceID;
    }
}
