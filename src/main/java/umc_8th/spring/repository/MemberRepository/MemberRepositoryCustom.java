package umc_8th.spring.repository.MemberRepository;

import umc_8th.spring.web.dto.MyPageDTO;

public interface MemberRepositoryCustom {
    MyPageDTO getMyPage(Long memberId);
}
