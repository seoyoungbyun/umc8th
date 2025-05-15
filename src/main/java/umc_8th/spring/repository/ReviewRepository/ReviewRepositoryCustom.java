package umc_8th.spring.repository.ReviewRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc_8th.spring.domain.Review;

public interface ReviewRepositoryCustom {
    void insertReview(String content, Double rating, Long memberId, Long storeId);
}
