package umc_8th.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_8th.spring.apiPayload.code.status.ErrorStatus;
import umc_8th.spring.apiPayload.exception.handler.MemberHandler;
import umc_8th.spring.apiPayload.exception.handler.StoreHandler;
import umc_8th.spring.converter.ReviewConverter;
import umc_8th.spring.domain.Member;
import umc_8th.spring.domain.Review;
import umc_8th.spring.domain.Store;
import umc_8th.spring.repository.MemberRepository.MemberRepository;
import umc_8th.spring.repository.ReviewRepository.ReviewRepository;
import umc_8th.spring.repository.StoreRepository.StoreRepository;
import umc_8th.spring.web.dto.ReviewRequestDTO;

@Service
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;

    private final StoreRepository storeRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Review joinReview(ReviewRequestDTO.ReviewJoinDto request, Long storeId){
        Store store = storeRepository.findStoreById(storeId);

        Long memberId = 1L; //하드 코딩
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Review review = ReviewConverter.toReview(request, store, member);
        return reviewRepository.save(review);
    }
}
