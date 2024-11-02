package com.placement.placement.Service;


import com.placement.placement.Entity.Drive;
import com.placement.placement.Repository.DriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriveService {
    @Autowired
    public DriveRepository driveRepository;

    public Drive findById(Long id){
//        System.out.println(id);
        return driveRepository.findById(id).orElseThrow();
    }
}
