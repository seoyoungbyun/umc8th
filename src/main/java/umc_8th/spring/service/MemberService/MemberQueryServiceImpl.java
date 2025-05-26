package umc_8th.spring.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.apiPayload.code.status.ErrorStatus;
import umc_8th.spring.apiPayload.exception.handler.MemberHandler;
import umc_8th.spring.config.security.jwt.JwtTokenProvider;
import umc_8th.spring.converter.MemberConverter;
import umc_8th.spring.domain.Member;
import umc_8th.spring.repository.MemberRepository.MemberRepository;
import umc_8th.spring.web.dto.MemberResponseDTO;
import umc_8th.spring.web.dto.MyPageDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public MyPageDTO getMyPage(Long memberId){
        return memberRepository.getMyPage(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDTO.MemberInfoDTO getMemberInfo(HttpServletRequest request){
        Authentication authentication = jwtTokenProvider.extractAuthentication(request);
        String email = authentication.getName();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        return MemberConverter.toMemberInfoDTO(member);
    }
}
