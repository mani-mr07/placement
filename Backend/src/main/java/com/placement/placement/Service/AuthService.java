package com.placement.placement.Service;

import com.placement.placement.Entity.*;
import com.placement.placement.Repository.StaffRepository;
import com.placement.placement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private EmailService emailService;
    public StaffLoginResponse staffLogin(StaffDTO staffdto) {
        Optional<Staff> staff=staffRepository.findByEmail(staffdto.getEmail());
       Staff staff1=staff.get();
       if(staff1.getPassword().equals(staffdto.getPassword())){
           return new StaffLoginResponse(staff1, otpService.generateAccessToken(staff1.getEmail(), staff1.getId(),Role.STAFF), otpService.generateRefreshToken(staff1.getEmail()));
       }
       throw new InvalidCredentialsException("Invalid email or password");
    }

    public ResponseEntity<String> studentLogin(StudentDTO studentDTO) {
//        Optional<Student>student=studentRepository.findByEmail(studentDTO.getEmail());
//        Student student1=student.get();
//
//        if(student1.getPassword().equals(studentDTO.getPassword())){
////            return new LoginResponse(student1, otpService.generateAccessToken(student1.getEmail(), student1.getId(),Role.STUDENT), otpService.generateRefreshToken(student1.getEmail()));
//        }
//        throw new InvalidCredentialsException("Invalid email or password");
        Optional<Student> student=studentRepository.findByEmail(studentDTO.getEmail());
        Student student1=student.get();
        Long otp=emailService.optGenerator();
        OTP otp1=new OTP();
        otp1.setStudent(student1);
//        otp1.setOtp(otp);
        student1.setOtp(otp);
        studentRepository.save(student1);
        if(student1!=null && student1.getPassword().equals(studentDTO.getPassword())){
            emailService.sendOtpEmail(student1.getEmail(), otp);
            return ResponseEntity.ok("otp is send to mail");
        }
        else{
            return ResponseEntity.ok("Invalid email or password");
        }

    }
    public LoginResponse verify(StudentDTO studentDTO, Long otp) {
        Optional<Student> student1=studentRepository.findByEmail(studentDTO.getEmail());
        Student student=student1.get();
        System.out.println("student otp"+student.getOtp());
        System.out.println("student-email"+studentDTO.getEmail());
        System.out.println("incoming otp"+otp);
        if(student!=null && student.getOtp().equals(otp)){
            return new LoginResponse(student,otpService.generateAccessToken(student.getEmail(), student.getId(),Role.STUDENT),otpService.generateRefreshToken(student.getEmail()));
        }
        throw new AuthService.InvalidCredentialsException("Invalid userRegNumber or otp");
    }

    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
