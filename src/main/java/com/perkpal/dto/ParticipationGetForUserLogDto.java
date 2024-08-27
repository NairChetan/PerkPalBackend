package com.perkpal.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;


@Data
public class ParticipationGetForUserLogDto{

        private String employeeFirstName;
        private String employeeLastName;
        private String activityName;
        private String activityIdCategoryName;
        private String description;
        private int duration;
        private String status;
        private String proof;
        private Timestamp participationDate;



}
