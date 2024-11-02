package com.placement.placement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="StudentDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String resume;
    private String skills;
    private float cgpa;
    private  int arrears;
    private boolean historyOfArrear;
    private boolean placed;

    @ManyToMany(mappedBy = "registeredStudents")
    private List<Drive> drivesRegistered;

    public void addRegisteredstudent(Drive drive){
        if(drivesRegistered.size()>0){
            drivesRegistered.add(drive);
        }
        else{
            drivesRegistered=new ArrayList<>();
            drivesRegistered.add(drive);
        }
    }
}
