    package com.perkpal.dto;

    import lombok.Data;

    @Data
    public class ParticipationPostDto {

        private String categoryName;
        private String activityName;
        private String description;
        private int duration;
        private String proofUrl;
        private Long createdBy;
        private Long employeeEmpId;
    }
