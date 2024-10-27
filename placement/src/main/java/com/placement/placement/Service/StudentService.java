package com.placement.placement.Service;

import com.placement.placement.Entity.Student;
import com.placement.placement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    public StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Student getOneStudents(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }
    public Student createStudent(Student student){
        return studentRepository.save(student);
    }
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
