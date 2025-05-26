package umc_8th.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc_8th.spring.config.security.jwt.JwtTokenProvider;
import umc_8th.spring.repository.MemberRepository.MemberRepository;
import umc_8th.spring.service.AuthService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    @Operation(summary = "새로운 토큰 발급 API - 인증 필요",
            description = "새로운 토큰을 발급받는 API입니다.",
            security = { @SecurityRequirement(name = "JWT TOKEN") }
    )
    public ResponseEntity<?> reissue(HttpServletRequest request) {
        Map<String, String> tokens = authService.reissueTokens(request);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + tokens.get("accessToken"))
                .header("Refresh-Token", tokens.get("refreshToken"))
                .build();
    }
}

