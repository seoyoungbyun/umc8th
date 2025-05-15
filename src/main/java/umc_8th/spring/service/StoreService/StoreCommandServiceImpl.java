package umc_8th.spring.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.apiPayload.code.status.ErrorStatus;
import umc_8th.spring.apiPayload.exception.handler.RegionHandler;
import umc_8th.spring.converter.StoreConverter;
import umc_8th.spring.domain.Region;
import umc_8th.spring.domain.Store;
import umc_8th.spring.repository.RegionRepository.RegionRepository;
import umc_8th.spring.repository.StoreRepository.StoreRepository;
import umc_8th.spring.web.dto.StoreRequestDTO;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService{

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public Store joinStore(StoreRequestDTO.StoreJoinDto request){
        Region region = regionRepository.findById(request.getRegionId()).orElseThrow(() -> new RegionHandler(ErrorStatus.REGION_NOT_FOUND));

        Store newStore = StoreConverter.toStore(request, region);
        return storeRepository.save(newStore);
    }
}

