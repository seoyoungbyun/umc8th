package umc_8th.spring.repository.StoreRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_8th.spring.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
}
