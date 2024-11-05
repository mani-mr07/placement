package com.placement.placement.Controller;
import com.placement.placement.Entity.*;
import com.placement.placement.Service.OTPService;
import com.placement.placement.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private OTPService otpService;


    @GetMapping
    public List<Staff> getAllStaff() {
        return staffService.getAllStaff();
    }
    @GetMapping("/login")
    public String login(@RequestBody StaffDTO staffDTO){
        return staffService.login(staffDTO);
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
    @PostMapping("/uploadDrive")
    public Drive uploadDrive(@RequestBody DriveDTO drive){
//        System.out.println("Received Drive with Company ID: " + (drive.getCompany() != null ? drive.getCompany().getId() : "null"));
//        System.out.println("company"+drive.getCompany());
//        if (drive.getCompany() == null || drive.getCompany().getId() == null) {
//            throw new IllegalArgumentException("Company ID is required to create a Drive.");
//        }
        return staffService.uploadDriveDetails(drive);
    }

    @PostMapping("/uploadCompany")
    public Company uploadCompany(@RequestBody Company company){
        return staffService.uploadCompanyDetails(company);
    }


//    @PostMapping("/generate")
//    public ResponseEntity<String> generateOtp(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//        String response = otpService.generateAndSendOTP(email);
//        return ResponseEntity.ok(response);
//    }

    // Endpoint to verify OTP
//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//        String otp = request.get("otp");
//        boolean isVerified = otpService.verifyOTP(email, otp);  // Implement this method in OTPService
//
//        if (isVerified) {
//            return ResponseEntity.ok("OTP verified successfully.");
//        } else {
//            return ResponseEntity.status(400).body("Invalid or expired OTP.");
//        }
    @PostMapping("/placedStudent")
    public Student placeStudent(@RequestBody StudentDriveDTO studentDriveDTO){
        return staffService.placedStudent(studentDriveDTO);
    }
}
