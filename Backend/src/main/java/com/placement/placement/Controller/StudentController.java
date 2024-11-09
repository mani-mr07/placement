package com.placement.placement.Controller;

import com.placement.placement.Entity.*;
import com.placement.placement.Service.EmailService;
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
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private EmailService emailService;


    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
//    @PostMapping("/login")
//    public String login(@RequestBody StudentDTO studentdto){
//        return studentService.login(studentdto);
//    }

    @PostMapping("/login")
    public String login(@RequestBody StudentDTO studentdto) {
        try {
            studentService.login(studentdto);
//            return ResponseEntity.ok("succcessfull"); // 200 OK status for successful login
            return "suucesfulll otp sent to mail";
        }
        catch (StudentService.InvalidCredentialsException e) {
        // Return 401 Unauthorized for invalid credentials
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            return "error";
    } catch (Exception e) {
        // Return 500 Internal Server Error for any other exceptions
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        return "error";
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

    @PostMapping("/registerForDrive")
    public ResponseEntity<String> registerforDrive(@RequestBody  StudentDriveDTO studentDriveDTO){
        System.out.println("hi");
        System.out.println(studentDriveDTO.getStudentID());
        System.out.println(studentDriveDTO.getdriveID());
        try {
            studentService.registerforDrive(studentDriveDTO);
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
    }
