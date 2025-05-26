package umc_8th.spring.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import umc_8th.spring.web.dto.MemberResponseDTO;
import umc_8th.spring.web.dto.MyPageDTO;

public interface MemberQueryService {
    MyPageDTO getMyPage(Long memberId);
    MemberResponseDTO.MemberInfoDTO getMemberInfo(HttpServletRequest request);
}
