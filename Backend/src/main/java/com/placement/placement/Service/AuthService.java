package com.placement.placement.Service;

import com.placement.placement.Entity.*;
import com.placement.placement.Repository.StaffRepository;
import com.placement.placement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private OTPService otpService;
    public StaffLoginResponse staffLogin(StaffDTO staffdto) {
        Optional<Staff> staff=staffRepository.findByEmail(staffdto.getEmail());
       Staff staff1=staff.get();
       if(staff1.getPassword().equals(staffdto.getPassword())){
           return new StaffLoginResponse(staff1, otpService.generateAccessToken(staff1.getEmail(), staff1.getId(),Role.STAFF), otpService.generateRefreshToken(staff1.getEmail()));
       }

       throw new InvalidCredentialsException("Invalid email or password");
    }

    public LoginResponse studentLogin(StudentDTO studentDTO) {
        Optional<Student>student=studentRepository.findByEmail(studentDTO.getEmail());
        Student student1=student.get();

        if(student1.getPassword().equals(studentDTO.getPassword())){
            return new LoginResponse(student1, otpService.generateAccessToken(student1.getEmail(), student1.getId(),Role.STUDENT), otpService.generateRefreshToken(student1.getEmail()));
        }
        throw new InvalidCredentialsException("Invalid email or password");

    }

    public class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
