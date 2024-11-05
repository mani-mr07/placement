package com.placement.placement.Service;

import com.placement.placement.Entity.*;
import com.placement.placement.Repository.CompanyRepository;
import com.placement.placement.Repository.DriveRepository;
import com.placement.placement.Repository.PlacedStudentRepository;
import com.placement.placement.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

//        if (drive.getCompany() != null && drive.getCompany().getId() != null) {
//            Long companyId = drive.getCompany().getId();
//            // Fetch the company by ID
//            Company company = companyRepository.findById(companyId)
//                    .orElseThrow(() -> new RuntimeException("Company with ID " + companyId + " not found."));
//
//            // Set the company in the drive
//            drive.setCompany(company);
//        } else {
//            throw new IllegalArgumentException("Company ID is required to create a Drive.");
//        }

        // Save the Drive
//        return driveRepository.save(drive);

        Drive drive = new Drive();
        drive.setDay(driveDTO.getDay());
        drive.setMonth(driveDTO.getMonth());
        drive.setYear(driveDTO.getYear());
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

    public String login(StaffDTO staffDTO) {
        Optional<Staff> staffOptional=staffRepository.findByEmail(staffDTO.getEmail());
        Staff staff=staffOptional.get();
        if(staff!=null && staff.getPassword().equals(staffDTO.getPassword())){
            return "allow";
        }
        return "not allow";
    }
}
