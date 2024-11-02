package com.placement.placement.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriveDTO {
    private int day;
    private int month;
    private int year;
    private double salary;
    private String description;
    private int eligibleCGPA;
    private boolean standingArrearAllowed;
    private boolean historyOfArrearAllowed;
    private Long companyId;
}
