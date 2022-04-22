package com.bignerdranch.android.blackboard.Blackboard.Search;

import java.util.List;


public class Search {

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

        private List<AnnouncementsDTO> announcements;

        private List<OrganizationsDTO> organizations;

        public List<AnnouncementsDTO> getAnnouncements() {
            return announcements;
        }

        public void setAnnouncements(List<AnnouncementsDTO> announcements) {
            this.announcements = announcements;
        }

        public List<OrganizationsDTO> getOrganizations() {
            return organizations;
        }

        public void setOrganizations(List<OrganizationsDTO> organizations) {
            this.organizations = organizations;
        }

        public static class AnnouncementsDTO {

            private Integer ID;

            private String CreatedAt;

            private String UpdatedAt;

            private Object DeletedAt;

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

        public static class OrganizationsDTO {

            private Integer ID;

            private String CreatedAt;

            private String UpdatedAt;

            private Object DeletedAt;

            private String founder_id;

            private String organization_name;

            private String intro;

            private String avatar;

            private String sha;

            private String path;

            public String getOrganization_name() {
                return organization_name;
            }

            public void setOrganization_name(String organization_name) {
                this.organization_name = organization_name;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }

    }

}
