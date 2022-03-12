package com.bignerdranch.android.blackboard1;

import java.io.Serializable;

public class Post implements Serializable {
    private String contents;//通知内容
    private String organizationName;//团队名
    private String updatedAt;//发布时间

    public Post(){

    }

    public Post(String contents, String organization_name, String updatedAt) {
        this.contents = contents;
        this.organizationName = organization_name;
        this.updatedAt = updatedAt;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getOrganization_name() {
        return organizationName;
    }

    public void setOrganization_name(String organization_name) {
        this.organizationName = organization_name;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "updatedAt='" + updatedAt + '\'' +
                ", contents='" + contents + '\'' +
                ", organizationName='" + organizationName + '\'' +
                '}';
    }

}
