package com.placement.placement.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="StaffDetails")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone;

//    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<OTP> otps=new HashSet<>();
//
//    public Set<OTP> getotp() {
//        return otps;
//    }


}