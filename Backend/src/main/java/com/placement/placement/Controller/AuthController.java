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

    @PostMapping("/staffLogin")
    public StaffLoginResponse login(@RequestBody StaffDTO staffdto) {
//        try {
//            staffService.login(staffdto);
////            System.out.println("hi");
//            String accesstoken=otpService.generateAccessToken(staffdto.getEmail());
//            String refreshtoken= otpService.generateRefreshToken(staffdto.getEmail());
////            System.out.println("welcome");
////            return ResponseEntity.ok("success");
//            return ResponseEntity.ok(new LoginResponse(null,true,accesstoken,refreshtoken));
//        }
//        catch (StudentService.InvalidCredentialsException e) {
//            // Return 401 Unauthorized for invalid credentials
//            System.out.println("bye");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        } catch (Exception e) {
//            // Return 500 Internal Server Error for any other exceptions
//            System.out.println("bye2");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
//        }
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
    @PostMapping("/studentLogin")
    public LoginResponse login(@RequestBody StudentDTO studentDTO){
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
}
