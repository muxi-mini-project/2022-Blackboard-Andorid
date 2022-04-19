package com.bignerdranch.android.blackboard.Mine.Post;

import java.io.Serializable;
import java.util.List;

//@lombok.NoArgsConstructor
//@lombok.Data
public class Post {
//    private String contents;//通知内容
//    private String organizationName;//团队名
//    private String updatedAt;//发布时间
//    @com.fasterxml.jackson.annotation.JsonProperty("code")
//    private Integer code;
//    @com.fasterxml.jackson.annotation.JsonProperty("message")
//    private String message;
//    @com.fasterxml.jackson.annotation.JsonProperty("data")
//    private List<DataDTO> data;
//
//    public Post(){
//
//    }
//
//    public Post(String contents, String organization_name, String updatedAt) {
//        this.contents = contents;
//        this.organizationName = organization_name;
//        this.updatedAt = updatedAt;
//    }
//
//    public String getContents() {
//        return contents;
//    }
//
//    public void setContents(String contents) {
//        this.contents = contents;
//    }
//
//    public String getOrganization_name() {
//        return organizationName;
//    }
//
//    public void setOrganization_name(String organization_name) {
//        this.organizationName = organization_name;
//    }
//
//    public String getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    @Override
//    public String toString() {
//        return "Post{" +
//                "updatedAt='" + updatedAt + '\'' +
//                ", contents='" + contents + '\'' +
//                ", organizationName='" + organizationName + '\'' +
//                '}';
//    }

    private Integer ID;

    private String CreatedAt;

    private String UpdatedAt;

    private String DeletedAt;

    private String publisher_id;

    private Integer organization_id;

    private String organization_name;

    private Integer group_id;

    private String group_name;

    private String contents;

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public String getContents() {
        return contents;
    }
}
