package umc_8th.spring.service.MemberService;

import umc_8th.spring.web.dto.MyPageDTO;

public interface MemberQueryService {
    MyPageDTO getMyPage(Long memberId);
}
