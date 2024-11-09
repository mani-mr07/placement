package com.placement.placement.Repository;

import com.placement.placement.Entity.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveRepository extends JpaRepository<Drive,Long> {

}
