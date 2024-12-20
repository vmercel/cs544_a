package edu.miu.cs.cs544.mercel.jpa.monitoring.vitals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VitalsRepository extends JpaRepository<Vitals, Long> {
    List<Vitals> findByUserId(Long userId); // Query method to filter vitals by userId
}
