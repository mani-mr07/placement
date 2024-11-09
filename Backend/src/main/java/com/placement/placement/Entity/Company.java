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
@Table(name="CompanyName")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Company{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<Drive> drives= new ArrayList<>();

}
