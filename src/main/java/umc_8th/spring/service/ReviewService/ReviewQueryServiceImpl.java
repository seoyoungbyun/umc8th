package umc_8th.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.repository.ReviewRepository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    @Override
    public void insertReview(String content, Double rating, Long memberId, Long storeId){
        reviewRepository.insertReview(content, rating, memberId, storeId);
    }
}
