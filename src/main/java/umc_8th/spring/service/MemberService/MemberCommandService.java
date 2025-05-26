package umc_8th.spring.service.MemberService;

import umc_8th.spring.domain.Member;
import umc_8th.spring.web.dto.MemberRequestDTO;
import umc_8th.spring.web.dto.MemberResponseDTO;

public interface MemberCommandService {
    Member joinMember(MemberRequestDTO.JoinDto request);
    MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginRequestDTO request);
}
