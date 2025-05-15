package umc_8th.spring.service.MemberMissionService;

import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.domain.mapping.MemberMission;

import java.util.Optional;

public interface MemberMissionQueryService {
    Optional<MemberMission> findMissionsForMemberByStatus(Long memberId, Long cursor, MissionStatus status);
}
