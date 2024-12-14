package com.placement.placement.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpVerifactionRequest {
    private StudentDTO studentDTO;
    private Long otp;
}
