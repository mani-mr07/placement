package com.placement.placement.Controller;
import com.placement.placement.Entity.*;
import com.placement.placement.Repository.CompanyRepository;
import com.placement.placement.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private DriveService driveService;

    @Autowired
    private CompayService compayService;

    @GetMapping
    public List<Staff> getAllStaff() {
        return staffService.getAllStaff();
    }


    @GetMapping("/{id}")
    public Staff getStaffById(@PathVariable Long id) {
        return staffService.getStaffById(id);
    }

    @PostMapping
    public Staff createStaff(@RequestBody Staff staff) {
        return staffService.createStaff(staff);
    }

    @PutMapping("/{id}")
    public Staff updateStaff(@RequestBody Staff staff) {
        return staffService.updateStaff(staff);
    }

    @DeleteMapping("/{id}")
    public void deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
    }
    @PostMapping("/{id}/uploadDrive")
    public Drive uploadDrive(@RequestBody DriveDTO drive){

        return staffService.uploadDriveDetails(drive);
    }

    @PostMapping("/uploadCompany")
    public Company uploadCompany(@RequestBody Company company){
        return staffService.uploadCompanyDetails(company);
    }



    @PostMapping("/placedStudent")
    public Student placeStudent(@RequestBody StudentDriveDTO studentDriveDTO){
        return staffService.placedStudent(studentDriveDTO);
    }

    @GetMapping("/retrieve/allStudent")
    public List<Student> getAllStudent(){
        return studentService.getAllStudents();
    }

    @GetMapping("/retrieve/oneStudent/{id}")
    public Student getOneStudent(@PathVariable Long id){
        return studentService.getOneStudents(id);
    }
    @GetMapping("retrieve/placedStudent")
    public List<Student>getPlacedStudent(){
    return studentService.getPlacedStudent();}

    @GetMapping("retrieve/nonPlacedStudent")
    public List<Student>getNonPlacedStudent(){
        return studentService.getNonPlacedStudent();
    }
    @GetMapping("retrieve/allDrive")
    public List<Drive>getAllDrive(){
       return  driveService.getAllDrive();
    }
    @GetMapping("retrieve/driveRegisteredStudent/{id}")
    public List<Student>getDriveRegisteredStudent(@PathVariable Long id){
        return driveService.getAllRegisteredStudent(id);
    }
    @GetMapping("retrieve/drivePlacedStudent/{id}")
    public List<Student>getDrivePlacedStudent(@PathVariable Long id){
        return staffService.getPlacedStudentsByDriveId(id);
    }
    @GetMapping("retrieve/allCompany")
    public List<Company>getAllCompany(HttpServletRequest request){
        Long userIdFromToken = (Long) request.getAttribute("userId");
        System.out.println("hi welcome staff");
        return compayService.getAllCompany();
    }
    @GetMapping("{id}/getDrives")
    public List<Drive>getDrivesById(@PathVariable Long id,HttpServletRequest request){
        Long userIdFromToken = (Long) request.getAttribute("userId");


        return staffService.getAllDriveByCompanyId(id);
    }
}
