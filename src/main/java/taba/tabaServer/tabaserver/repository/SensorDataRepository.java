package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taba.tabaServer.tabaserver.domain.DrivingSession;
import taba.tabaServer.tabaserver.domain.SensorData;

import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findSensorDataByDrivingSessionId(Long drivingSessionId);

    List<SensorData> findAllByDrivingSession(DrivingSession drivingSession);
}
