package com.placement.placement.Service;

import com.placement.placement.Entity.*;
import com.placement.placement.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CompayService compayService;

    @Autowired
    private PlacedStudentRepository placedStudentRepository;

    @Autowired
    private  DriveService driveService;
    @Autowired
    private DriveRepository driveRepository;
    @Autowired
    private  CompanyRepository companyRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElseThrow();
    }

    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Staff updateStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }

    public Drive uploadDriveDetails(DriveDTO driveDTO) {

        Drive drive = new Drive();
        drive.setDate(driveDTO.getDate());
        drive.setSalary(driveDTO.getSalary());
        drive.setDescription(driveDTO.getDescription());
        drive.setEligibleCGPA(driveDTO.getEligibleCGPA());
        drive.setStandingArrearLimit(driveDTO.getStandingArrearLimit());
        drive.setHistoryOfArrearAllowed(driveDTO.isHistoryOfArrearAllowed());

        long id=driveDTO.getCompanyId();
        Company company = compayService.findById(id);
        drive.setCompany(company);
        return driveRepository.save(drive);
    }
    public Company uploadCompanyDetails(Company company){
        return companyRepository.save(company);
    }

    public Student placedStudent(StudentDriveDTO studentDriveDTO){
        Student student=studentService.getOneStudents(studentDriveDTO.getStudentID());
        Drive drive=driveService.findById(studentDriveDTO.getdriveID());
        student.setPlaced(true);
        PlacedStudent placedStudent=new PlacedStudent();
        placedStudent.setStudent(student);
        placedStudent.setPlacementStatus("placed");
        placedStudent.setDrive(drive);

        placedStudentRepository.save(placedStudent);
        return student;
    }

    public List<Student> getPlacedStudentsByDriveId(Long driveId) {
        return placedStudentRepository.findByDriveId(driveId);
    }

    public List<Drive> getAllDriveByCompanyId(Long id) {
        Optional<Company>company=companyRepository.findById(id);
        Company company1=company.get();
        List<Drive>drives=company1.getDrives();
        System.out.println(drives);
        return drives;
    }



public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
    public void login(StaffDTO staffDTO){
        Optional<Staff> staff=staffRepository.findByEmail(staffDTO.getEmail());
        Staff staff1=staff.get();
        if(staff1!=null && staff1.getPassword().equals(staffDTO.getPassword())){
            return;
        }
        else{
            throw new StaffService.InvalidCredentialsException("Invalid email or password");
        }
    }
}
