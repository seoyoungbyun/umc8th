package umc_8th.spring.repository.MemberRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc_8th.spring.domain.QMember;
import umc_8th.spring.web.dto.MyPageDTO;

import static com.querydsl.core.types.Projections.constructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private final QMember member;

    @Override
    public MyPageDTO getMyPage(Long memberId){
        BooleanBuilder predicate = new BooleanBuilder();

        if (memberId != null){
            predicate.and(member.id.eq(memberId));
        }

        return jpaQueryFactory
                .select(constructor(MyPageDTO.class,
                        member.id,
                        member.profileImage,
                        member.name,
                        member.email,
                        member.phoneAuth,
                        member.phoneNum,
                        member.points
                ))
                .from(member)
                .where(predicate)
                .fetchOne();
    }
}
