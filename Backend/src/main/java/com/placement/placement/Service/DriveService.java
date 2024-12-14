package com.placement.placement.Service;


import com.placement.placement.Entity.Drive;
import com.placement.placement.Entity.Student;
import com.placement.placement.Repository.DriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriveService {
    @Autowired
    public DriveRepository driveRepository;

    public Drive findById(Long id){
        return driveRepository.findById(id).orElseThrow();
    }

    public List<Drive> getAllDrive() {
        return driveRepository.findAll();
    }

    public List<Student> getAllRegisteredStudent(Long id) {
        Drive drive=driveRepository.findById(id).orElseThrow();
        return drive.getRegisteredStudents();
    }

}
