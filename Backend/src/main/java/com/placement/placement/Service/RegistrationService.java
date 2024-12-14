package com.placement.placement.Service;

import com.placement.placement.Entity.Registration;
import com.placement.placement.Repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Registration getRegistrationById(Long id) {
        return registrationRepository.findById(id).orElseThrow();
    }

    public Registration createRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    public Registration updateRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }
}