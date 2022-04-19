package com.bignerdranch.android.blackboard.Settings.Change;


public class UploadAvatar {

    private Integer code;

    private String message;

    private DataDTO data;

    public DataDTO getData() {
        return data;
    }


    public static class DataDTO {

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
