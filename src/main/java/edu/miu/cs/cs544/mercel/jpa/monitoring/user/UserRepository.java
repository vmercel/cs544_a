package edu.miu.cs.cs544.mercel.jpa.monitoring.user;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByEmail(String mail);

    UserEntity findByUsername(String username);

    @Query("SELECT u FROM UserEntity u JOIN u.foodLogs f WHERE f.logDate = :logDate")
    List<UserEntity> findUsersByFoodLogDate(@Param("logDate") LocalDate logDate);


    @Query("""
    SELECT DISTINCT u FROM UserEntity u
    JOIN u.foodLogs f
    JOIN u.vitals v
    WHERE f.logDate = :date AND v.recordDate = :date
""")
    List<UserEntity> findUsersWithFoodLogsAndVitalsOnDate(@Param("date") LocalDate date);
}

