package com.bignerdranch.android.blackboard.Bean.Organization;

import android.widget.LinearLayout;

import java.util.List;

public class Organization
{

    private int ID;
    private String organization_name;
    private String intro;
    private String group_name;

    private String CreatedAt;
    private String UpdatedAt;
    private Object DeletedAt;
    private String founderId;
    private String Avatar;
    private String Sha;
    private String Path;



    /*构造器*/
    // 创建组织(name，intro）
    public Organization(String organization_name, String intro) {
        this.organization_name = organization_name;
        this.intro = intro;
    }
    //创建话题（organization_name，organization_id，group_name)
    public Organization(int ID, String organization_name, String group_name) {
        this.ID = ID;
        this.organization_name = organization_name;
        this.group_name = group_name;
    }
    // 获取组织详情（name)
    public Organization( String name) {
        this.organization_name = organization_name;
    }


    /*获取值*/
    //名字
    public String getOrganization_name() {
        return organization_name;
    }
    //简介
    public String getIntro() {
        return intro;
    }
    //ID
    public int getID() {
        return ID;
    }

    public String getAvatar() {
        return Avatar;
    }


}
