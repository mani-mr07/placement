package com.placement.placement.Controller;

import com.placement.placement.Entity.*;
import com.placement.placement.Service.DriveService;
import com.placement.placement.Service.EmailService;
import com.placement.placement.Service.OTPService;
import com.placement.placement.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private DriveService driveService;


    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
//    @PostMapping("/login")
//    public String login(@RequestBody StudentDTO studentdto){
//        return studentService.login(studentdto);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody StudentDTO studentdto) {
        try {
          Student student=  studentService.login(studentdto);
            String accesstoken=otpService.generateAccessToken(studentdto.getEmail());
String refreshtoken= otpService.generateRefreshToken(studentdto.getEmail());
//            return ResponseEntity.ok("succcessfull"); // 200 OK status for successful login
//            return "suucesfulll otp sent to mail";
            System.out.println("hi");
            return ResponseEntity.ok(new LoginResponse(student,true,accesstoken,refreshtoken));

        }
        catch (StudentService.InvalidCredentialsException e) {
        // Return 401 Unauthorized for invalid credentials
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            return ResponseEntity.badRequest().body(new LoginResponse(null,false,null,null));
    } catch (Exception e) {
        // Return 500 Internal Server Error for any other exceptions
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
            return ResponseEntity.badRequest().body(new LoginResponse(null,false,null,null));

        }
    }
//@GetMapping("/login")
//public String login(@RequestParam String email, @RequestParam String password) {
//    StudentDTO studentdto = new StudentDTO();
//    studentdto.setEmail(email);
//    studentdto.setPassword(password);
//    return studentService.login(studentdto);
//}

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getOneStudents(id);
    }
    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/{driveId}/{studentId}/registerForDrive")
    public ResponseEntity<String> registerforDrive(@PathVariable Long driveId,@PathVariable Long studentId){
        System.out.println("hi");
//        System.out.println(studentDriveDTO.getStudentID());
//        System.out.println(studentDriveDTO.getdriveID());
        try {
            studentService.registerforDrive(driveId,studentId);
            return ResponseEntity.ok("Successfully Registered");
        }
        catch (StudentService.InvalidCredentialsException e) {
            // Return 401 Unauthorized for invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // Return 500 Internal Server Error for any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
    @PostMapping("log")
    public String log(@RequestParam Long RegisterNumbeer){
        System.out.println("hi");
        if(studentService.log(RegisterNumbeer)){
            System.out.println("welcome");
            return "otp sent to mail";
        }
        return "something happens";
    }
    @PostMapping("verify-otp")
    public String verify(@RequestParam Long RegisterNumber ,@RequestParam int otp){
        if(studentService.verify(RegisterNumber,otp)){
            return "allow";
        }
        else{
            return "not allow";
        }
    }
    @GetMapping("/{id}/retrieve-non-registered-drive")
    public List<Drive>nonRegisteredDrive(@PathVariable Long id){
        System.out.println("hi");
        return studentService.nonRegisteredDrive(id);
    }

    @GetMapping("/{id}/retrieve-registered-drive")
    public List<Drive>registeredDrive(@PathVariable Long id){
        return studentService.registeredDrive(id);
    }
    @GetMapping("/reterive/{driveId}")
    public Drive viewDrive(@PathVariable Long driveId){
        return  driveService.findById(driveId);
    }
    }
