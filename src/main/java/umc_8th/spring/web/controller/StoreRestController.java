package umc_8th.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc_8th.spring.apiPayload.ApiResponse;
import umc_8th.spring.converter.MissionConverter;
import umc_8th.spring.converter.ReviewConverter;
import umc_8th.spring.converter.StoreConverter;
import umc_8th.spring.domain.Mission;
import umc_8th.spring.domain.Review;
import umc_8th.spring.domain.Store;
import umc_8th.spring.service.MissionService.MissionCommandService;
import umc_8th.spring.service.MissionService.MissionQueryService;
import umc_8th.spring.service.ReviewService.ReviewCommandService;
import umc_8th.spring.service.ReviewService.ReviewQueryService;
import umc_8th.spring.service.StoreService.StoreCommandService;
import umc_8th.spring.service.StoreService.StoreQueryService;
import umc_8th.spring.validation.annotation.CheckPage;
import umc_8th.spring.validation.annotation.ExistStore;
import umc_8th.spring.web.dto.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreRestController {

    private final StoreCommandService storeCommandService;

    private final ReviewQueryService reviewQueryService;

    private final ReviewCommandService reviewCommandService;

    private final MissionQueryService missionQueryService;

    private final MissionCommandService missionCommandService;

    @PostMapping("/")
    public ApiResponse<StoreResponseDTO.StoreJoinResultDTO> join(@RequestBody @Valid StoreRequestDTO.StoreJoinDto request){
        Store store = storeCommandService.joinStore(request);
        return ApiResponse.onSuccess(StoreConverter.toJoinResultDTO(store));
    }

    @PostMapping("/{storeId}/reviews")
    public ApiResponse<ReviewResponseDTO.ReviewJoinResultDTO> join(
            @ExistStore @PathVariable Long storeId,
            @RequestBody @Valid ReviewRequestDTO.ReviewJoinDto request){
        Review review = reviewCommandService.joinReview(request, storeId);
        return ApiResponse.onSuccess(ReviewConverter.toJoinResultDTO(review));
    }

    @PostMapping("/{store_id}/missions")
    public ApiResponse<MissionResponseDTO.MissionJoinResultDTO> join(
            @ExistStore @PathVariable Long store_id,
            @RequestBody @Valid MissionRequestDTO.MissionJoinDto request){
        Mission mission = missionCommandService.joinMission(request, store_id);
        return ApiResponse.onSuccess(MissionConverter.toJoinResultDTO(mission));
    }

    @GetMapping("/{storeId}/reviews")
    @Operation(summary = "특정 가게의 리뷰 목록 조회 API",description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!")
    })
    public ApiResponse<ReviewResponseDTO.ReviewPreViewListDTO> getReviewList(@ExistStore @PathVariable(name = "storeId") Long storeId,
                                                                             @CheckPage @RequestParam(name = "page") Integer page){
        reviewQueryService.getReviewList(storeId,page);
        return null;
    }

    @GetMapping("{storeId}/missions/view")
    @Operation(summary = "특정 가게의 미션 목록 조회 API",description = "특정 가게의 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!")
    })
    public ApiResponse<MissionResponseDTO.MissionPreViewListDTO> getMissionList(@ExistStore @PathVariable(name = "storeId") Long storeId, @CheckPage @RequestParam(name = "page") Integer page){
        page = page - 1;
        Page<Mission> missionList = missionQueryService.getMissionList(storeId, page);
        return ApiResponse.onSuccess(MissionConverter.missionPreViewListDTO(missionList));
    }

}
