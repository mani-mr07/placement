package com.placement.placement.Service;

import com.placement.placement.Entity.Company;
import com.placement.placement.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompayService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllDrives() {
        return companyRepository.findAll();
    }

    public Company getDriveById(Long id) {
        return companyRepository.findById(id).orElseThrow();
    }

    public Company createDrive(Company company) {
        return companyRepository.save(company);
    }

    public Company updateDrive(Company company) {
        return companyRepository.save(company);
    }

    public void deleteDrive(Long id) {
        companyRepository.deleteById(id);
    }
}
