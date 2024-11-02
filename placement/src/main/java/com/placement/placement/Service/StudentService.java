package com.placement.placement.Service;

import com.placement.placement.Entity.Drive;
import com.placement.placement.Entity.Registration;
import com.placement.placement.Entity.Student;
import com.placement.placement.Entity.StudentDriveDTO;
import com.placement.placement.Repository.DriveRepository;
import com.placement.placement.Repository.RegistrationRepository;
import com.placement.placement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    public DriveRepository driveRepository;

    @Autowired
    public DriveService driveService;

    @Autowired
    public RegistrationRepository registrationRepository;


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
    public Registration registerforDrive(StudentDriveDTO studentDriveDTO){
        System.out.println("welcome");
//System.out.println(studentDriveDTO.getDriveID());
        Drive drive=driveService.findById(studentDriveDTO.getdriveID());
        System.out.println("drive successfulll");
        Student student=getOneStudents(studentDriveDTO.getStudentID());
        System.out.println("student successfull");
        if(drive!=null && student!=null){
            System.out.println("mapla");
            drive.addRegisteredstudent(student);
            student.addRegisteredstudent(drive);
            Registration registration=new Registration();
            registration.setStudent(student);
            registration.setStatus("registered");
            return registrationRepository.save(registration);
        }
            else {
            throw new IllegalArgumentException("Drive ID is required to create a Drive.");
    }

    }

}
