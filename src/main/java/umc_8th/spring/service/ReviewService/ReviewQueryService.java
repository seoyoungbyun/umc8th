package umc_8th.spring.service.ReviewService;

import org.springframework.data.domain.Page;
import umc_8th.spring.domain.Review;

public interface ReviewQueryService {
    void insertReview(String content, Double rating, Long memberId, Long storeId);
    Page<Review> getReviewList(Long StoreId, Integer page);
}
