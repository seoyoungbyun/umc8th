package umc_8th.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.repository.MemberRepository.MemberRepository;
import umc_8th.spring.web.dto.MyPageDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    @Override
    public MyPageDTO getMyPage(Long memberId){
        return memberRepository.getMyPage(memberId);
    }
}
