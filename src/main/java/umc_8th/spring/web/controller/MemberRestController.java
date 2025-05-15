package umc_8th.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc_8th.spring.apiPayload.ApiResponse;
import umc_8th.spring.converter.MemberConverter;
import umc_8th.spring.converter.MemberMissionConverter;
import umc_8th.spring.domain.Member;
import umc_8th.spring.domain.mapping.MemberMission;
import umc_8th.spring.service.MemberMissionService.MemberMissionCommandService;
import umc_8th.spring.service.MemberService.MemberCommandService;
import umc_8th.spring.validation.annotation.IsAlreadyChallenging;
import umc_8th.spring.web.dto.MemberRequestDTO;
import umc_8th.spring.web.dto.MemberResponseDTO;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberCommandService memberCommandService;

    private final MemberMissionCommandService memberMissionCommandService;

    @PostMapping("/")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDto request){
        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/missions")
    public ApiResponse<MemberResponseDTO.MemberMissionJoinResultDTO> join(@RequestBody @IsAlreadyChallenging @Valid MemberRequestDTO.MemberMissionJoinDto request){
        MemberMission memberMission = memberMissionCommandService.joinMemberMission(request);
        return ApiResponse.onSuccess(MemberMissionConverter.toJoinResultDTO(memberMission));
    }
}
