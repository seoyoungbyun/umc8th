package umc_8th.spring.repository.ReviewRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import umc_8th.spring.domain.*;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Override
    public void insertReview(String content, Double rating, Long memberId, Long storeId){
        Member member = null;
        Store store = null;

        if (memberId != null){
            member = jpaQueryFactory.selectFrom(QMember.member)
                    .where(QMember.member.id.eq(memberId))
                    .fetchOne();
        }
        if (storeId != null){
            store = jpaQueryFactory.selectFrom(QStore.store)
                    .where(QStore.store.id.eq(storeId))
                    .fetchOne();
        }

        if (content != null && rating != null && member != null && store != null) {
            Review review = Review.builder()
                    .content(content)
                    .rating(rating)
                    .member(member)
                    .store(store)
                    .build();
            entityManager.persist(review);
        }
    }
}
