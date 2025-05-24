package umc_8th.spring.service.MissionService;

import org.springframework.data.domain.Page;
import umc_8th.spring.domain.Mission;
import umc_8th.spring.web.dto.MissionRequestDTO;

public interface MissionCommandService {
    Mission joinMission(MissionRequestDTO.MissionJoinDto request, Long storeId);
    Page<Mission> completeMission(Long memberId, Long missionId, Integer page);
}
