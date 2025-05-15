package umc_8th.spring.service.MemberMissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.apiPayload.code.status.ErrorStatus;
import umc_8th.spring.apiPayload.exception.handler.MemberMissionHandler;
import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.domain.mapping.MemberMission;
import umc_8th.spring.repository.MemberMissionRepository.MemberMissionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMissionQueryServiceImpl implements MemberMissionQueryService {

    private final MemberMissionRepository memberMissionRepository;

    @Override
    public Optional<MemberMission> findMissionsForMemberByStatus(Long memberId, Long missionId, MissionStatus status){
        return memberMissionRepository.findByMemberIdAndMissionIdAndStatus(memberId, missionId, status);
    }
}
