package com.placement.placement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

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
    private Long RegisterNumber;
    private String email;
    private String password;
    private String Department;
    private Date dob;
    private Long phone;
    private String resume;
    private String skills;
    private float cgpa;
    private  int arrears;
    private boolean historyOfArrear;
    private boolean placed;

    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;
    private Long otp;

    private LocalDateTime otpExpiration;

//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<OTP> otps=new HashSet<>();

//    public Set<OTP> getotp() {
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
