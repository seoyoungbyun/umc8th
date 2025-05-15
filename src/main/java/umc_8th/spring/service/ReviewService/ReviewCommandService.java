package umc_8th.spring.service.ReviewService;

import umc_8th.spring.domain.Review;
import umc_8th.spring.web.dto.ReviewRequestDTO;

public interface ReviewCommandService {
    Review joinReview(ReviewRequestDTO.ReviewJoinDto request, Long storeId);
}
