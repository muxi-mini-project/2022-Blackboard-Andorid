package com.bignerdranch.android.blackboard.Mine.Favourite;

import java.util.List;

public class Favorites {

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

        private String UpdatedAt;

        private String DeletedAt;

        private String student_id;

        private Integer announcement_id;

        private String announcement;

        public String getAnnouncement() {
            return announcement;
        }

        public void setAnnouncement(String announcement) {
            this.announcement = announcement;
        }
    }
}

