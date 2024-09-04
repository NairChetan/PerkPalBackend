    package com.perkpal.dto;

    import lombok.Data;

    import java.sql.Timestamp;

    @Data
    public class ParticipationPostDto {

        private String categoryName;
        private String activityName;
        private String description;
        private int duration;
        private String proofUrl;
        private Timestamp participationDate;
        private Long createdBy;
        private Long employeeEmpId;
    }
