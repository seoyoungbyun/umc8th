package umc_8th.spring.service.StoreService;


import umc_8th.spring.domain.Store;
import umc_8th.spring.web.dto.StoreRequestDTO;

public interface StoreCommandService {
    Store joinStore(StoreRequestDTO.StoreJoinDto request);
}
