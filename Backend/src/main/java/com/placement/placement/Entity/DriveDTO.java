package com.placement.placement.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DriveDTO {
    private Date date;
    private double salary;
    private String description;
    private int eligibleCGPA;
    private int standingArrearLimit;
    private boolean historyOfArrearAllowed;
    private Long companyId;
}
