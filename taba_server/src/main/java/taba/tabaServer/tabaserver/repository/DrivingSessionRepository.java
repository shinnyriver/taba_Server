package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taba.tabaServer.tabaserver.domain.DrivingSession;

import java.util.Optional;

public interface DrivingSessionRepository extends JpaRepository<DrivingSession, Long> {
    Optional<DrivingSession> findDrivingSessionByUserId(Long userId);
}
