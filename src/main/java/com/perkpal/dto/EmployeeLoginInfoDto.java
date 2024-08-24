package com.perkpal.dto;

import lombok.Data;

@Data
public class EmployeeLoginInfoDto {
    private Long id;
    private String email;
    private String roleName;
    private String firstName;
    private String lastName;
    private String duName;
    private String photoUrl;
    private String clubName;

}
