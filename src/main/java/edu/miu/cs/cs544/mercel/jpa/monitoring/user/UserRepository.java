package edu.miu.cs.cs544.mercel.jpa.monitoring.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String mail);

    UserEntity findByUsername(String username);
}

