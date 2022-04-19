package com.bignerdranch.android.blackboard.Bean.Topic;

public class Topics
{
    //topic的id
    private Integer ID;
    private Integer organization_id;
    private String organization_name;
    private String group_name;

    private String createdAt;
    private String updatedAt;
    private Object deletedAt;

    public Topics(Integer organization_id, String organization_name, String group_name)
    {
        this.organization_id = organization_id;
        this.organization_name = organization_name;
        this.group_name = group_name;
    }

    public String getGroupName() {
        return group_name;
    }

}
