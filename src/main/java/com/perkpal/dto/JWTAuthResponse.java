package com.perkpal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String clubName;
    private String duName;
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private String roleName;
}
