package umc_8th.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import umc_8th.spring.service.ReviewService.ReviewCommandService;
import umc_8th.spring.service.StoreService.StoreCommandService;
import umc_8th.spring.validation.annotation.ExistStore;
import umc_8th.spring.web.dto.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreRestController {

    private final StoreCommandService storeCommandService;

    private final ReviewCommandService reviewCommandService;

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

}
