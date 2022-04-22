package com.bignerdranch.android.blackboard.Bean.Message;

public class MessageItem
{

    private String contents;
    private String group_name;
    private String organization_name;

    private Integer ID;
    private String createdAt;
    private String updatedAt;
    private Object deletedAt;
    private String publisherId;
    private Integer organizationId;
    private Integer groupId;

    /*构造器*/
    //发布通知
    public MessageItem(String organization_name, String group_name, String contents) {
        this.organization_name = organization_name;
        this.group_name = group_name;
        this.contents = contents;
    }

    public MessageItem(String contents, String group_name, String organization_name, Integer ID) {
        this.contents = contents;
        this.group_name = group_name;
        this.organization_name = organization_name;
        this.ID = ID;
    }

    /*取值*/
    public String getContents() {
        return contents;
    }
    public Integer getOrganizationId() {
        return organizationId;
    }
    public String getOrganization_name() {
        return organization_name;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public Integer getID() {
        return ID;
    }
}
