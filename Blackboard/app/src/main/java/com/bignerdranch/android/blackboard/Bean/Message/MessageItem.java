package com.bignerdranch.android.blackboard.Bean.Message;

public class MessageItem
{

    private String group_name;
    private String contents;
    private String organization_name;

    private Integer id;
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


}
