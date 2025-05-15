package umc_8th.spring.service.MemberMissionService;

import umc_8th.spring.domain.mapping.MemberMission;
import umc_8th.spring.web.dto.MemberRequestDTO;

public interface MemberMissionCommandService {
    MemberMission joinMemberMission(MemberRequestDTO.MemberMissionJoinDto request);
}
