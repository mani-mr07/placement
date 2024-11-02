package com.placement.placement.Controller;

import com.placement.placement.Entity.Registration;
import com.placement.placement.Entity.Student;
import com.placement.placement.Entity.StudentDriveDTO;
import com.placement.placement.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getOneStudents(id);
    }
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/registerForDrive")
    public Registration registerforDrive(@RequestBody  StudentDriveDTO studentDriveDTO){
        System.out.println("hi");
        System.out.println(studentDriveDTO.getStudentID());
        System.out.println(studentDriveDTO.getdriveID());
        return studentService.registerforDrive(studentDriveDTO);
    }
    }
