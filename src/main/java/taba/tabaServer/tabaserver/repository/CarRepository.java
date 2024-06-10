package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taba.tabaServer.tabaserver.domain.Car;
import taba.tabaServer.tabaserver.enums.CarSize;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByUserId(Long user_id);

    Optional<Car> findCarByCarNumber(String carNumber);

    @Query("SELECT COUNT(c) FROM Car c where c.carSize = :carSize")
    Long countByCarSize(CarSize carSize);
}
