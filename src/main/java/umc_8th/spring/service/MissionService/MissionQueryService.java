package umc_8th.spring.service.MissionService;

import org.springframework.data.domain.Page;
import umc_8th.spring.domain.Mission;
import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.web.dto.HomeDTO;
import umc_8th.spring.web.dto.MissionByStatusDTO;

import java.util.List;

public interface MissionQueryService {
    List<MissionByStatusDTO> findMissionByMissionStatus(Long memberId, Long cursor, MissionStatus status);
    HomeDTO getHome(Long memberId, Long regionId, Long cursor);
    Page<Mission> getMissionListByStore(Long storeId, Integer page);
    Page<Mission> getMissionListByMember(Long memberId, Integer page);
}
