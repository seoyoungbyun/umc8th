package umc_8th.spring.service.StoreService;

import umc_8th.spring.domain.Store;

import java.util.List;
import java.util.Optional;

public interface StoreQueryService {
    Optional<Store> findStore(Long id);
    List<Store> findStoresByNameAndScore(String name, Double rating);
}
