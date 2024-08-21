package com.perkpal.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String description;
}