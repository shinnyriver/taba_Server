package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taba.tabaServer.tabaserver.domain.Calibration;
import taba.tabaServer.tabaserver.domain.Car;
import taba.tabaServer.tabaserver.enums.SensorType;

import java.util.Optional;

public interface CalibrationRepository extends JpaRepository<Calibration, Long> {
    Optional<Calibration> findBySensorType(SensorType sensorType);
    Optional<Calibration> findByCarId(Long carId);
}
