package umc_8th.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.repository.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberAgreeRepository memberAgreeRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberPreferRepository memberPreferRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void deleteMember(Long memberId) {
        // @Modifying 사용
        reviewRepository.deleteByMemberId(memberId);
        memberAgreeRepository.deleteByMemberId(memberId);
        memberMissionRepository.deleteByMemberId(memberId);
        memberPreferRepository.deleteByMemberId(memberId);

        memberRepository.deleteById(memberId);//부모 삭제 (orphanRemoval 적용)
    }
}
