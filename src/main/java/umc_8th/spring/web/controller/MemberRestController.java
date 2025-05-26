package umc_8th.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc_8th.spring.apiPayload.ApiResponse;
import umc_8th.spring.converter.MemberConverter;
import umc_8th.spring.converter.MemberMissionConverter;
import umc_8th.spring.converter.MissionConverter;
import umc_8th.spring.converter.ReviewConverter;
import umc_8th.spring.domain.Member;
import umc_8th.spring.domain.Mission;
import umc_8th.spring.domain.Review;
import umc_8th.spring.domain.mapping.MemberMission;
import umc_8th.spring.service.MemberMissionService.MemberMissionCommandService;
import umc_8th.spring.service.MemberService.MemberCommandService;
import umc_8th.spring.service.MemberService.MemberQueryService;
import umc_8th.spring.service.MissionService.MissionCommandService;
import umc_8th.spring.service.MissionService.MissionQueryService;
import umc_8th.spring.service.ReviewService.ReviewQueryService;
import umc_8th.spring.validation.annotation.CheckPage;
import umc_8th.spring.validation.annotation.ExistStore;
import umc_8th.spring.validation.annotation.IsAlreadyChallenging;
import umc_8th.spring.web.dto.MemberRequestDTO;
import umc_8th.spring.web.dto.MemberResponseDTO;
import umc_8th.spring.web.dto.MissionResponseDTO;
import umc_8th.spring.web.dto.ReviewResponseDTO;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberQueryService memberQueryService;

    private final MemberCommandService memberCommandService;

    private final ReviewQueryService reviewQueryService;

    private final MissionQueryService missionQueryService;

    private final MissionCommandService missionCommandService;

    private final MemberMissionCommandService memberMissionCommandService;

    @PostMapping("/join")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDto request){
        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/missions")
    public ApiResponse<MemberResponseDTO.MemberMissionJoinResultDTO> join(@RequestBody @IsAlreadyChallenging @Valid MemberRequestDTO.MemberMissionJoinDto request){
        MemberMission memberMission = memberMissionCommandService.joinMemberMission(request);
        return ApiResponse.onSuccess(MemberMissionConverter.toJoinResultDTO(memberMission));
    }

    @PostMapping("/login")
    @Operation(summary = "유저 로그인 API",description = "유저가 로그인하는 API입니다.")
    public ApiResponse<MemberResponseDTO.LoginResultDTO> login(@RequestBody @Valid MemberRequestDTO.LoginRequestDTO request) {
        return ApiResponse.onSuccess(memberCommandService.loginMember(request));
    }

    @GetMapping("/info")
    @Operation(summary = "유저 내 정보 조회 API - 인증 필요",
            description = "유저가 내 정보를 조회하는 API입니다.",
            security = { @SecurityRequirement(name = "JWT TOKEN") }
    )
    public ApiResponse<MemberResponseDTO.MemberInfoDTO> getMyInfo(HttpServletRequest request) {
        return ApiResponse.onSuccess(memberQueryService.getMemberInfo(request));
    }

    @GetMapping("{memberId}/reviews/view")
    @Operation(summary = "내가 작성한 리뷰 목록 조회 API",description = "내가 작성한 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "나의 아이디, path variable 입니다!")
    })
    public ApiResponse<ReviewResponseDTO.ReviewPreViewListDTO> getReviewList(
            @PathVariable(name = "memberId") Long memberId,
            @ExistStore @RequestParam(name = "storeId") Long storeId,
            @RequestParam(name = "page") Integer page){
        Page<Review> reviewList = reviewQueryService.getReviewList(memberId, storeId, page);
        return ApiResponse.onSuccess(ReviewConverter.reviewPreViewListDTO(reviewList));
    }

    @GetMapping("{memberId}/missions/challenging/view")
    @Operation(summary = "내가 진행 중인 미션 목록 조회 API",description = "내가 진행 중인 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "나의 아이디, path variable 입니다!")
    })
    public ApiResponse<MissionResponseDTO.MissionPreViewListDTO> getMissionList(
            @PathVariable(name = "memberId") Long memberId,
            @CheckPage @RequestParam(name = "page") Integer page){
        page = page - 1;
        Page<Mission> missionList = missionQueryService.getMissionListByMember(memberId, page);
        return ApiResponse.onSuccess(MissionConverter.missionPreViewListDTO(missionList));
    }

    @PatchMapping("{memberId}/missions/complete/view")
    @Operation(summary = "진행 중인 미션 진행 완료로 바꾸기",description = "진행 중인 미션 진행 완료로 바꾸는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "나의 아이디, path variable 입니다!")
    })
    public ApiResponse<MissionResponseDTO.MissionPreViewListDTO> completeMissionList(
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(name = "missionId") Long missionId,
            @CheckPage @RequestParam(name = "page") Integer page){
        page = page - 1;
        Page<Mission> missionList = missionCommandService.completeMission(memberId, missionId, page);
        return ApiResponse.onSuccess(MissionConverter.missionPreViewListDTO(missionList));
    }
}
