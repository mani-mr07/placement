package com.placement.placement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="otp")
@Getter
@Setter
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int otpid;
    private int otpn;
    private LocalDateTime createdTime;
    @ManyToOne(fetch= FetchType.LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name = "id", nullable = false)
    @JsonIgnore
    private Student student;

    public void setOtp(int otpn) {
        this.otpn = otpn;
    }
    @PrePersist
    protected void onCreate() {
        this.createdTime=LocalDateTime.now();
    }

    public int getOtp() {
        return otpn;
    }
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(createdTime.plusMinutes(3));
    }

}

