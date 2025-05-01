package umc_8th.spring.service.ReviewService;

public interface ReviewQueryService {
    void insertReview(String content, Double rating, Long memberId, Long storeId);
}
