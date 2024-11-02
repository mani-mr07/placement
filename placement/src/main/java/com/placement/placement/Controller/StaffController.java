package com.placement.placement.Controller;
import com.placement.placement.Entity.Company;
import com.placement.placement.Entity.Drive;
import com.placement.placement.Entity.DriveDTO;
import com.placement.placement.Entity.Staff;
import com.placement.placement.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

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
}
