package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taba.tabaServer.tabaserver.domain.SensorData;

import java.util.Optional;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    Optional<SensorData> findSensorDataByDrivingSessionId(Long id);
}
