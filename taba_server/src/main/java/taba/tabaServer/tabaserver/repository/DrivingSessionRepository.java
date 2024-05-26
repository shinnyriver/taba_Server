package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taba.tabaServer.tabaserver.domain.DrivingSession;
import taba.tabaServer.tabaserver.enums.ErrorStatus;

import java.time.LocalDate;
import java.util.List;


public interface DrivingSessionRepository extends JpaRepository<DrivingSession, Long> {
    List<DrivingSession> findDrivingSessionByUserId(Long userId);
    List<DrivingSession> findByErrorStatus(ErrorStatus errorStatus);
    List<DrivingSession> findAllByStartDateBetween(LocalDate start, LocalDate end);
}
