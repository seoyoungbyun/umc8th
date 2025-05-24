package umc_8th.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.domain.Member;
import umc_8th.spring.domain.Mission;
import umc_8th.spring.domain.Store;
import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.domain.mapping.MemberMission;
import umc_8th.spring.repository.MemberMissionRepository.MemberMissionRepository;
import umc_8th.spring.repository.MemberRepository.MemberRepository;
import umc_8th.spring.repository.MissionRepository.MissionRepository;
import umc_8th.spring.repository.StoreRepository.StoreRepository;
import umc_8th.spring.web.dto.HomeDTO;
import umc_8th.spring.web.dto.MissionByStatusDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MissionRepository missionRepository;

    private final StoreRepository storeRepository;

    private final MemberRepository memberRepository;

    private final MemberMissionRepository memberMissionRepository;

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

    @Override
    public Page<Mission> getMissionListByStore(Long storeId, Integer page) {
        Store store = storeRepository.findById(storeId).get();

        Page<Mission> StorePage = missionRepository.findAllByStore(store, PageRequest.of(page, 10));
        return StorePage;
    }

    @Override
    public Page<Mission> getMissionListByMember(Long userId, Integer page) {
        Member member = memberRepository.findById(userId).get();
        Page<MemberMission> memberMissions = memberMissionRepository.findAllByMemberAndStatus(member, MissionStatus.CHALLENGING, PageRequest.of(page, 10));
        Page<Mission> StorePage = memberMissions.map(MemberMission::getMission);
        return StorePage;
    }
}
