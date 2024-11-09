package com.placement.placement.Repository;

import com.placement.placement.Entity.Company;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
//    @Override
//    Company<Company> findById(Long id);

    @Override
    Optional<Company> findById(Long aLong);
}
