package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taba.tabaServer.tabaserver.domain.Calibration;
import taba.tabaServer.tabaserver.enums.SensorType;

import java.util.List;
import java.util.Optional;

public interface CalibrationRepository extends JpaRepository<Calibration, Long> {
    List<Calibration> findAllByCar_CarId(Long carId);

    Optional<Calibration> findByCar_CarIdAndSensorType(Long carId, SensorType sensorType);

    List<Calibration> findAllByCar_CarName(String carName);
}
