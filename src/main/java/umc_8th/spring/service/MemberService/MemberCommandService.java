package umc_8th.spring.service.MemberService;

import umc_8th.spring.domain.Member;
import umc_8th.spring.web.dto.MemberRequestDTO;

public interface MemberCommandService {
    Member joinMember(MemberRequestDTO.JoinDto request);
}
