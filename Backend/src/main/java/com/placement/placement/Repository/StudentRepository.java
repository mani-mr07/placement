package com.placement.placement.Repository;

import com.placement.placement.Entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    @Query("select  u from Student u where u.RegisterNumber=:RegisterNumber")
    Optional<Student> finByregisterNumber(Long RegisterNumber);

//    Optional<userDe> findByUserEmail(String email);

//    @Modifying
//    @Transactional
//    @Query("DELETE FROM otp o WHERE o.createdTime < :expiryTime" )
//    void deleteExpiredOtps(@Param("expiryTime") LocalDateTime expiryTime);
}
