package umc_8th.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.converter.MissionConverter;
import umc_8th.spring.domain.Mission;
import umc_8th.spring.domain.Store;
import umc_8th.spring.repository.MissionRepository.MissionRepository;
import umc_8th.spring.repository.StoreRepository.StoreRepository;
import umc_8th.spring.web.dto.MissionRequestDTO;

@Service
@RequiredArgsConstructor
public class MissionCommandServiceImpl implements MissionCommandService {

    private final MissionRepository missionRepository;

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public Mission joinMission(MissionRequestDTO.MissionJoinDto request, Long storeId) {

        Store store = storeRepository.findStoreById(storeId);
        Mission mission = MissionConverter.toMission(request, store);

        return missionRepository.save(mission);
    }
}
