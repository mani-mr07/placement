package com.placement.placement.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private Student student;
    private boolean success;
    private String accessToken;
    private String refreshToken;
}
