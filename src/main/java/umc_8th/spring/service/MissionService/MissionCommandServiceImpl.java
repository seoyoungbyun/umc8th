package umc_8th.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.converter.MissionConverter;
import umc_8th.spring.domain.Member;
import umc_8th.spring.domain.Mission;
import umc_8th.spring.domain.Store;
import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.domain.mapping.MemberMission;
import umc_8th.spring.repository.MemberMissionRepository.MemberMissionRepository;
import umc_8th.spring.repository.MemberRepository.MemberRepository;
import umc_8th.spring.repository.MissionRepository.MissionRepository;
import umc_8th.spring.repository.StoreRepository.StoreRepository;
import umc_8th.spring.web.dto.MissionRequestDTO;

@Service
@RequiredArgsConstructor
public class MissionCommandServiceImpl implements MissionCommandService {

    private final MissionRepository missionRepository;

    private final StoreRepository storeRepository;

    private final MemberRepository memberRepository;

    private final MemberMissionRepository memberMissionRepository;

    @Override
    @Transactional
    public Mission joinMission(MissionRequestDTO.MissionJoinDto request, Long storeId) {

        Store store = storeRepository.findStoreById(storeId);
        Mission mission = MissionConverter.toMission(request, store);

        return missionRepository.save(mission);
    }

    @Override
    public Page<Mission> completeMission(Long userId, Long missionId, Integer page) {
        Member member = memberRepository.findById(userId).get();
        memberMissionRepository.findByMemberIdAndMissionId(userId, missionId)
                .ifPresent(memberMission -> {
                    // 상태 변경
                    memberMission.setStatus(MissionStatus.COMPLETE);
                    memberMissionRepository.save(memberMission);
                });

        Page<MemberMission> memberMissions = memberMissionRepository.findAllByMemberAndStatus(member, MissionStatus.COMPLETE, PageRequest.of(page, 10));
        Page<Mission> StorePage = memberMissions.map(MemberMission::getMission);
        return StorePage;
    }
}
