package com.bignerdranch.android.blackboard;

import android.widget.Button;

public class RelativeOrganization {
    private String organizationName;
    private String organizationIntroduction;

    public RelativeOrganization(){

    }

    public RelativeOrganization(String organizationName, String organizationIntroduction) {
        this.organizationName = organizationName;
        this.organizationIntroduction = organizationIntroduction;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationIntroduction() {
        return organizationIntroduction;
    }

    public void setOrganizationIntroduction(String organizationIntroduction) {
        this.organizationIntroduction = organizationIntroduction;
    }

}
