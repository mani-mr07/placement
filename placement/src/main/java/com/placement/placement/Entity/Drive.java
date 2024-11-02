package com.placement.placement.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="CompanyDetails")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Drive {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int driveId;
    private int day;
    private int month;
    private int year;
    private double salary;
    private String description;

    private int eligibleCGPA;
    private boolean standingArrearAllowed;
    private boolean historyOfArrearAllowed;

    @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
    @JoinColumn(name = "id")
    @JsonIgnore
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "drive_student_registration",
            joinColumns = @JoinColumn(name = "drive_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> registeredStudents;
    @OneToMany(mappedBy = "drive")
    private List<PlacedStudent> placedStudents;

    public void addRegisteredstudent(Student student){
        if(registeredStudents.size()>0){
            registeredStudents.add(student);
        }
        else{
            registeredStudents=new ArrayList<>();
            registeredStudents.add(student);
        }
    }
}
