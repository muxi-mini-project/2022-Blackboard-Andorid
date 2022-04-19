package com.bignerdranch.android.blackboard.Mine.Post;

import java.util.List;


public class Posts {

    private Integer code;

    private String message;

    private List<DataDTO> data;


    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {

        private Integer ID;

        private String CreatedAt;

        private String updatedAt;

        private String deletedAt;

        private String publisher_id;

        private Integer organization_id;

        private String organization_name;

        private Integer group_id;

        private String group_name;

        private String contents;

        public String getCreatedAt() {
            return CreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            CreatedAt = createdAt;
        }

        public String getOrganization_name() {
            return organization_name;
        }

        public void setOrganization_name(String organization_name) {
            this.organization_name = organization_name;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }
    }
}
