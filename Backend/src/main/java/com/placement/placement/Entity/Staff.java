package com.placement.placement.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name="StaffDetails")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Staff implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role = Role.STAFF;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return roles or authorities; if no roles, return an empty list
//        return List.of(() -> role.name());
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));

    }


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