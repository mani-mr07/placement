package com.placement.placement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String password;
    private String phone;
    private String resume;
    private String skills;
    private float cgpa;
    private  int arrears;
    private boolean historyOfArrear;
    private boolean placed;

//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<OTPStudent> otps=new HashSet<>();
//
//    public Set<OTPStudent> getotp() {
//        return otps;
//    }

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
    public String getpassword(){
        return this.password;
    }
}
