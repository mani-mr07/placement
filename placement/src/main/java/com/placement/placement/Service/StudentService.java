package com.placement.placement.Service;

import com.placement.placement.Entity.*;
import com.placement.placement.Repository.DriveRepository;
import com.placement.placement.Repository.RegistrationRepository;
import com.placement.placement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public String login(StudentDTO studentDTO){
        Optional<Student> student=studentRepository.findByEmail(studentDTO.getEmail());
        Student student1=student.get();
        if(student1!=null && student1.getpassword().equals(studentDTO.getPassword())){
            return "allow";
        }
        return "not allow";
    }
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

public Registration registerforDrive(StudentDriveDTO studentDriveDTO) {
    System.out.println("welcome");

    // Retrieve the drive by ID
    Drive drive = driveService.findById(studentDriveDTO.getdriveID());
    if (drive == null) {
        throw new IllegalArgumentException("Drive ID is required to create a Drive.");
    }
    System.out.println("Drive retrieved successfully");

    // Retrieve the student by ID
    Student student = getOneStudents(studentDriveDTO.getStudentID());
    if (student == null) {
        throw new IllegalArgumentException("Student ID is required to register for the Drive.");
    }
    System.out.println("Student retrieved successfully");


    if (student.isPlaced()) {
        throw new IllegalArgumentException("Student is already placed and cannot register for a new drive.");
    }


    if (student.getCgpa() < drive.getEligibleCGPA()) {
        throw new IllegalArgumentException("Student's CGPA does not meet the minimum eligibility criteria.");
    }


    if (student.isHistoryOfArrear() && !drive.isHistoryOfArrearAllowed()) {
        throw new IllegalArgumentException("History of arrears is not allowed for this drive.");
    }

    if (student.getArrears()>drive.getStandingArrearLimit()) {
        throw new IllegalArgumentException("Standing arrears are not allowed for this drive.");
    }

    System.out.println("All validations passed, proceeding with registration");

    drive.addRegisteredstudent(student);
    student.addRegisteredstudent(drive);


    Registration registration = new Registration();
    registration.setStudent(student);
    registration.setDrive(drive);
    registration.setStatus("registered");

    return registrationRepository.save(registration);
}





}
