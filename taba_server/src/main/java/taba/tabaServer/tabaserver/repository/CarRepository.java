package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taba.tabaServer.tabaserver.domain.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
