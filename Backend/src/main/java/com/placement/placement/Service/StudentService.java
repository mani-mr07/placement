package com.placement.placement.Service;

import com.placement.placement.Entity.*;
import com.placement.placement.Repository.DriveRepository;
import com.placement.placement.Repository.RegistrationRepository;
import com.placement.placement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    public OTPService otpService;

    @Autowired
    public EmailService emailService;

    public List<Student> getNonPlacedStudent() {
        List<Student>students=studentRepository.findAll();
        List<Student>nonPlacedStudents=new ArrayList<>();
        for(Student student:students){
            if(student.isPlaced()==false){
                nonPlacedStudents.add(student);
            }
        }
        return nonPlacedStudents;
    }

    public boolean log(Long registerNumbeer) {
        Optional<Student> student1=studentRepository.finByregisterNumber(registerNumbeer);
        Student student=student1.get();
        System.out.println("hello");
        if(student!=null){
            System.out.println("student found");
            Long otp=emailService.optGenerator();
            student.setOtp(Long.valueOf(otp));
            studentRepository.save(student);
            System.out.println("student dosent has any problem");
            emailService.sendOtpEmail(student.getEmail(),otp);

            return true;
        }
        else{
            return false;
        }
    }



    public List<Drive>nonRegisteredDrive(Long id) {
        List<Drive>driveList=driveRepository.findAll();
        List<Drive>NonRegisteredDrive=new ArrayList<>();
        Optional<Student> student1=studentRepository.findById(id);
        Student student=student1.get();
        for (Drive drive:driveList){
            if(!drive.getRegisteredStudents().contains(student)){
                NonRegisteredDrive.add(drive);
            }
        }
        return NonRegisteredDrive;
    }
    public List<Drive>registeredDrive(Long id) {
        List<Drive>driveList=driveRepository.findAll();
        List<Drive>RegisteredDrive=new ArrayList<>();
        Optional<Student> student1=studentRepository.findById(id);
        Student student=student1.get();
        for (Drive drive:driveList){
            if(drive.getRegisteredStudents().contains(student)){
                RegisteredDrive.add(drive);
            }
        }
        return RegisteredDrive;
    }

    public class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
    public Student login(StudentDTO studentDTO){
        Optional<Student> student=studentRepository.findByEmail(studentDTO.getEmail());
        Student student1=student.get();
     Long otp=emailService.optGenerator();
     OTP otp1=new OTP();
     otp1.setStudent(student1);
//     otp1.setOtp(otp);
        if(student1!=null && student1.getPassword().equals(studentDTO.getPassword())){
            emailService.sendOtpEmail(student1.getEmail(), otp);
            return student1;
        }
        else{
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public List<Student>getPlacedStudent(){
        List<Student>list=new ArrayList<>();
        List<Student>students=studentRepository.findAll();
        for(int i=0;i<students.size();i++){
            if(students.get(i).isPlaced()==true){
                list.add(students.get(i));
            }
        }
        return list;
    }

    public Student getOneStudents(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }
    public ResponseEntity<String> createStudent(Student student){
        if(studentRepository.findByEmail(student.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Student already registered with this email.");
        }
         studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Student registered successfully");
    }
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

public void registerforDrive(Long driveId ,Long studentId) {
    System.out.println("welcome");

    // Retrieve the drive by ID
    Drive drive = driveService.findById(driveId);
    if (drive == null) {
        throw new IllegalArgumentException("Drive ID is required to create a Drive.");
    }
    System.out.println("Drive retrieved successfully");

    // Retrieve the student by ID
    Student student = getOneStudents(studentId);
    if (student == null) {
        throw new IllegalArgumentException("Student ID is required to register for the Drive.");
    }
    System.out.println("Student retrieved successfully");


    if (student.isPlaced()) {
        System.out.println("student is placed");
        throw new IllegalArgumentException("Student is already placed and cannot register for a new drive.");
    }


    if (student.getCgpa() < drive.getEligibleCGPA()) {
        System.out.println("cgpa is less");

        throw new IllegalArgumentException("Student's CGPA does not meet the minimum eligibility criteria.");
    }


    if (student.isHistoryOfArrear() && !drive.isHistoryOfArrearAllowed()) {
        System.out.println("arrear");

        throw new IllegalArgumentException("History of arrears is not allowed for this drive.");
    }

    if (student.getArrears()>drive.getStandingArrearLimit()) {
        System.out.println("more arrear");

        throw new IllegalArgumentException("Standing arrears are not allowed for this drive.");
    }
    if(drive.getRegisteredStudents().contains(student)){
        System.out.println("already registered");
        throw new IllegalArgumentException("You are already registered for this drive.");
    }

    System.out.println("All validations passed, proceeding with registration");

    drive.addRegisteredstudent(student);
    student.addRegisteredstudent(drive);


    Registration registration = new Registration();
    registration.setStudent(student);
    registration.setDrive(drive);
    registration.setStatus("registered");
    registrationRepository.save(registration);
    }




    public UserDetailsService userDetailsService()
    {
        return username -> studentRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));
    }




}
