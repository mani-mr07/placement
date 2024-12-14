package com.placement.placement.Repository;

import com.placement.placement.Entity.PlacedStudent;
import com.placement.placement.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlacedStudentRepository extends JpaRepository<PlacedStudent,Long> {
    @Query("SELECT ps.student FROM PlacedStudent ps WHERE ps.drive.id = :driveId")
    List<Student> findByDriveId(@Param("driveId") Long driveId);
}
