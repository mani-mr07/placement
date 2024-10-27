package com.placement.placement.Repository;

import com.placement.placement.Entity.PlacedStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacedStudentRepository extends JpaRepository<PlacedStudent,Long> {
}
