package com.bignerdranch.android.blackboard.Mine;

public class Information {


    private Integer code;
    private String message;
    private DataDTO data;

    public DataDTO getData() {
        return data;
    }

    public static class DataDTO {
        private Integer id;
        private String createdAt;
        private String updatedAt;
        private Object deletedAt;
        private String studentID;
        private String nickname;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }


}