package taba.tabaServer.tabaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taba.tabaServer.tabaserver.domain.Manager;
import taba.tabaServer.tabaserver.enums.ManagerType;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    List<Manager> findByManagerType(ManagerType managerType);
    Manager findByLoginId(String loginId);
}
