package umc_8th.spring.repository.StoreRepository;

import umc_8th.spring.domain.Store;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> dynamicQueryWithBooleanBuilder(String name, Double rating);
}
