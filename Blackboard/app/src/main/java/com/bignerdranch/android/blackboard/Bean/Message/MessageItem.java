package com.bignerdranch.android.blackboard.Bean.Message;

public class MessageItem
{

    private String groupName;
    private String contents;
    private String organizationName;

    private Integer id;
    private String createdAt;
    private String updatedAt;
    private Object deletedAt;
    private String publisherId;
    private Integer organizationId;
    private Integer groupId;

    /*构造器*/
    //发布通知
    public MessageItem(String organizationName, String groupName, String contents) {
        this.organizationName = organizationName;
        this.groupName = groupName;
        this.contents = contents;
    }


}
