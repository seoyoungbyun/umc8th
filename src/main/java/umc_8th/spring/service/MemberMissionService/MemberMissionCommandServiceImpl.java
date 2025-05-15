package umc_8th.spring.service.MemberMissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.apiPayload.code.status.ErrorStatus;
import umc_8th.spring.apiPayload.exception.handler.MemberHandler;
import umc_8th.spring.apiPayload.exception.handler.MemberMissionHandler;
import umc_8th.spring.converter.MemberMissionConverter;
import umc_8th.spring.domain.Member;
import umc_8th.spring.domain.Mission;
import umc_8th.spring.domain.mapping.MemberMission;
import umc_8th.spring.repository.MemberMissionRepository.MemberMissionRepository;
import umc_8th.spring.repository.MemberRepository.MemberRepository;
import umc_8th.spring.repository.MissionRepository.MissionRepository;
import umc_8th.spring.web.dto.MemberRequestDTO;

@Service
@RequiredArgsConstructor
public class MemberMissionCommandServiceImpl implements MemberMissionCommandService {

    private final MemberRepository memberRepository;

    private final MissionRepository missionRepository;

    private final MemberMissionRepository memberMissionRepository;

    @Override
    @Transactional
    public MemberMission joinMemberMission(MemberRequestDTO.MemberMissionJoinDto request) {
        //Member member = memberRepository.findRandomMember();
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Mission mission = missionRepository.findById(request.getMissionId()).orElseThrow(() -> new MemberMissionHandler(ErrorStatus.MISSION_NOT_FOUND));

        boolean isChallenging = memberMissionRepository
                .findByMemberIdAndMissionId(member.getId(), request.getMissionId())
                .isPresent();

        if (isChallenging) {
            throw new MemberMissionHandler(ErrorStatus.MEMBER_MISSION_FOUND);
        }

        MemberMission memberMission = MemberMissionConverter.toMemberMission(request, member, mission);

        return memberMissionRepository.save(memberMission);
    }
}
