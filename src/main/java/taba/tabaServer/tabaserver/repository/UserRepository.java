package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taba.tabaServer.tabaserver.domain.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    @Query("SELECT COUNT(u) FROM User u where u.createdAt BETWEEN :start AND :end")
    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(u) FROM User u where u.withdrawAt BETWEEN :start AND :end")
    Long countByWithdrawAtBetween(LocalDateTime start, LocalDateTime end);

}
