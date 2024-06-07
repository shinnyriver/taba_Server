package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taba.tabaServer.tabaserver.domain.Manager;
import taba.tabaServer.tabaserver.enums.ManagerType;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    List<Manager> findByManagerType(ManagerType managerType);

    Optional<Manager> findByLoginId(String loginId);
}
