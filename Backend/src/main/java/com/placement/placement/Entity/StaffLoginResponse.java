package com.placement.placement.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StaffLoginResponse {
    private Staff staff;
    private String accessToken;
    private String refreshToken;
}
