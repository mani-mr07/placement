package com.placement.placement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="StudentDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student implements UserDetails {
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return roles or authorities; if no roles, return an empty list
        return List.of(() -> role.name());    }

    @Override
    public String getPassword() {
        return this.password; // Ensure your password field is mapped correctly
    }

    @Override
    public String getUsername() {
        return this.email; // Use email as the username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Modify based on your application's needs
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Modify based on your application's needs
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modify based on your application's needs
    }

    @Override
    public boolean isEnabled() {
        return true; // Modify based on your application's needs
    }
}

