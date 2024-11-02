package com.placement.placement.Entity;

import lombok.*;

@Getter
@Setter
public class StudentDriveDTO {
    private Long studentID;
    private Long DriveID;
    public Long getdriveID(){
        return this.DriveID;
    }
}
