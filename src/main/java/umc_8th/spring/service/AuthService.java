package umc_8th.spring.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import umc_8th.spring.apiPayload.code.status.ErrorStatus;
import umc_8th.spring.apiPayload.exception.handler.MemberHandler;
import umc_8th.spring.config.security.jwt.JwtTokenProvider;
import umc_8th.spring.converter.MemberConverter;
import umc_8th.spring.domain.Member;
import umc_8th.spring.domain.RefreshToken;
import umc_8th.spring.repository.MemberRepository.MemberRepository;
import umc_8th.spring.repository.RefreshTokenRepository.RefreshTokenRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public Map<String, String> reissueTokens(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.resolveToken(request);
        System.out.println("확인1");
        if(refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            throw new MemberHandler(ErrorStatus.INVALID_TOKEN);
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
        System.out.println("확인2");
        String email = authentication.getName();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // DB에 저장된 Refresh Token과 일치 여부 확인
        RefreshToken savedToken = refreshTokenRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_TOKEN));
        System.out.println("확인3");

        if (!savedToken.getToken().equals(refreshToken)) {
            throw new MemberHandler(ErrorStatus.INVALID_TOKEN);
        }
        System.out.println("확인4");

        // 5. 새로운 토큰 생성
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                email, null,
                Collections.singleton(() -> member.getRole().name())
        );

        String newAccessToken = jwtTokenProvider.generateAccessToken(newAuthentication);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(newAuthentication);

        // 6. DB에 Refresh Token 갱신
        savedToken.setToken(newRefreshToken);
        refreshTokenRepository.save(savedToken);

        // 7. 반환
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken);
        return tokens;
    }
}
