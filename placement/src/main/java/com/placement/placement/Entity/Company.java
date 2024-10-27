package com.placement.placement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Table(name="CompanyDetails")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private Date date;
    private float salary;
    private  int elgibileCgpa;
    private  boolean historyOfArrearAllowed;
    private boolean arrearsAllowed;
    private String description;
}
