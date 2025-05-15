package umc_8th.spring.converter;

import umc_8th.spring.domain.Member;
import umc_8th.spring.domain.Review;
import umc_8th.spring.domain.Store;
import umc_8th.spring.web.dto.ReviewRequestDTO;
import umc_8th.spring.web.dto.ReviewResponseDTO;

import java.time.LocalDateTime;

public class ReviewConverter {

    public static ReviewResponseDTO.ReviewJoinResultDTO toJoinResultDTO(Review review){
        return ReviewResponseDTO.ReviewJoinResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Review toReview(ReviewRequestDTO.ReviewJoinDto request, Store store, Member member){

        return Review.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .store(store)
                .member(member)
                .build();
    }

}
