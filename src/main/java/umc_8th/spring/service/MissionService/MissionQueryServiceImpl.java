package umc_8th.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.domain.Store;
import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.repository.MissionRepository.MissionRepository;
import umc_8th.spring.web.dto.HomeDTO;
import umc_8th.spring.web.dto.MissionByStatusDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MissionRepository missionRepository;

    @Override
    public List<MissionByStatusDTO> findMissionByMissionStatus(Long memberId, Long cursor, MissionStatus status){
        List<MissionByStatusDTO> filteredMissions = missionRepository.findMissionByMissionStatus(memberId, cursor, status);

        filteredMissions.forEach(mission -> System.out.println("Mission: " + mission));

        return filteredMissions;
    }

    @Override
    public HomeDTO getHome(Long memberId, Long regionId, Long cursor){
        return missionRepository.getHome(memberId, regionId, cursor);
    }
}
