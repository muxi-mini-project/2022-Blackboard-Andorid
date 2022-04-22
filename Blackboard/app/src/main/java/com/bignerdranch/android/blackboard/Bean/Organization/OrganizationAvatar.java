package com.bignerdranch.android.blackboard.Bean.Organization;


public class OrganizationAvatar {

    private Integer code;

    private String message;

    private DataDTO data;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {

        private Integer Id;

        private String path;

        private String sha;

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
