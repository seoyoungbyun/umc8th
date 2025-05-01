package umc_8th.spring.repository.MissionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_8th.spring.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionRepositoryCustom {
}
