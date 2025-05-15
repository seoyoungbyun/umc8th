package umc_8th.spring.repository.MissionRepository;

import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.web.dto.HomeDTO;
import umc_8th.spring.web.dto.MissionByStatusDTO;

import java.util.List;

public interface MissionRepositoryCustom {
    List<MissionByStatusDTO> findMissionByMissionStatus(Long memberId, Long cursor, MissionStatus status);
    HomeDTO getHome(Long memberId, Long regionId, Long cursor);
}
