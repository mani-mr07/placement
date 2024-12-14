package com.placement.placement.Controller;

import com.placement.placement.Entity.*;
import com.placement.placement.Service.AuthService;
import com.placement.placement.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/Auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private StudentService studentService;


    //staff Login
    @PostMapping("/staffLogin")
    public StaffLoginResponse login(@RequestBody StaffDTO staffdto) {
        try{
            return  authService.staffLogin(staffdto);
        }
        catch (AuthService.InvalidCredentialsException e){
            throw e;
        }
        catch (Exception e){
            throw e;
        }
    }


    //student login
    @PostMapping("/studentLogin")
    public ResponseEntity<String> login(@RequestBody StudentDTO studentDTO){
        try{

            return authService.studentLogin(studentDTO);
        }
        catch (AuthService.InvalidCredentialsException e){
            throw e;
        }
        catch (Exception e){
            throw e;
        }

    }
    @PostMapping("/studentSignup")
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        System.out.println("hi");
        return studentService.createStudent(student);
    }

    @PostMapping("verify-otp")
    public LoginResponse verifyotp(@RequestBody OtpVerifactionRequest otpVerifactionRequest){
        StudentDTO studentDTO=otpVerifactionRequest.getStudentDTO();
        Long otp= otpVerifactionRequest.getOtp();
        return authService.verify(studentDTO,otp);
    }
}
