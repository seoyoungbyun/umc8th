package umc_8th.spring.repository.RegionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_8th.spring.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
