package com.bignerdranch.android.blackboard;

public class Post  {
    private String contents;//通知内容
    private String organization_name;//团队名
    private String updatedAt;
    private String object;

    public Post(String contents, String organization_name, String updatedAt) {
        this.contents = contents;
        this.organization_name = organization_name;
        this.updatedAt = updatedAt;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
