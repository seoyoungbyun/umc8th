package umc_8th.spring.repository.MissionRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc_8th.spring.domain.QMember;
import umc_8th.spring.domain.QMission;
import umc_8th.spring.domain.QRegion;
import umc_8th.spring.domain.QStore;
import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.domain.mapping.QMemberMission;
import umc_8th.spring.web.dto.HomeDTO;
import umc_8th.spring.web.dto.MissionByStatusDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private final QMission mission = QMission.mission;
    private final QMission missionSub = new QMission("missionSub");
    private final QMemberMission memberMission = QMemberMission.memberMission;
    private final QStore store = QStore.store;
    private final QMember member = QMember.member;
    private final QRegion region = QRegion.region;

    @Override
    public List<MissionByStatusDTO> findMissionByMissionStatus(Long memberId, Long cursor, MissionStatus status) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (memberId != null) {
            predicate.and(memberMission.member.id.eq(memberId));
        }

        if (status != null) {
            predicate.and(memberMission.status.eq(status));
        }

        if (cursor != null) {
            predicate.and(memberMission.updatedAt.lt(
                    JPAExpressions  //서브쿼리 작성 시 사용
                            .select(memberMission.updatedAt)
                            .from(memberMission)
                            .where(memberMission.id.eq(cursor))
            ));
        }

        List<Tuple> result = jpaQueryFactory
                .select(mission.id, mission.name, mission.description, mission.point, memberMission.status, store.name)
                .from(mission)
                .join(memberMission).on(memberMission.mission.eq(mission))
                .join(store).on(mission.store.id.eq(store.id))
                .where(predicate)
                .orderBy(memberMission.updatedAt.desc())
                .limit(15)
                .fetch();

        return result.stream()
                .map(tuple -> new MissionByStatusDTO(
                        tuple.get(mission.id),
                        tuple.get(mission.name),
                        tuple.get(mission.description),
                        tuple.get(mission.point),
                        tuple.get(memberMission.status),
                        tuple.get(store.name)
                ))
                .collect(Collectors.toList());
    }

    @Override
    public HomeDTO getHome(Long memberId, Long regionId, Long cursor){
        //화면 위 성공한 미션 개수 구하기
        BooleanBuilder predicate = new BooleanBuilder();

        if (memberId != null) {
            predicate.and(memberMission.member.id.eq(memberId));
        }
        if (regionId != null) {
            predicate.and(region.id.eq(regionId));
        }
        predicate.and(memberMission.status.eq(MissionStatus.COMPLETE));

        Tuple upInfo = jpaQueryFactory
                .select(region.name, memberMission.count())
                .from(mission)
                .join(memberMission).on(mission.id.eq(memberMission.mission.id))
                .join(store).on(mission.store.id.eq(store.id))
                .join(region).on(store.region.id.eq(region.id))
                .where(predicate)
                .groupBy(region.name)
                .fetchOne();

        String regionName = upInfo != null ? upInfo.get(region.name) : null;
        Long successMission = upInfo != null ? upInfo.get(memberMission.count()) : 0L;

        //도전 가능한 미션 목록
        predicate = new BooleanBuilder();

        ComparableExpression<String> cursorExpr = Expressions.stringTemplate(
                "CAST(CONCAT(LPAD(DATE_FORMAT({0}, '%Y-%m-%d'), 10, '0'), LPAD(CAST({1} AS CHAR), 10, '0')) AS CHAR)",
                mission.deadline, mission.id
        );

        ComparableExpression<String> lastCursorExpr = Expressions.stringTemplate(
                "CAST(CONCAT(LPAD(DATE_FORMAT({0}, '%Y-%m-%d'), 10, '0'), LPAD(CAST({1} AS CHAR), 10, '0')) AS CHAR)",
                missionSub.deadline, missionSub.id
        );

        if (regionId != null) {
            predicate.and(store.region.id.eq(regionId));
        }

        if (memberId != null) {
            predicate.and(mission.id.notIn(
                    JPAExpressions
                            .select(memberMission.mission.id)
                            .from(memberMission)
                            .where(memberMission.member.id.eq(memberId))
            ));
        }

        if (cursor != null) {
            LocalDate cursorDeadline = jpaQueryFactory
                    .select(missionSub.deadline)
                    .from(missionSub)
                    .where(missionSub.id.eq(cursor))
                    .fetchOne();

            if (cursorDeadline != null) {
                predicate.and(
                        mission.deadline.gt(cursorDeadline)
                                .or(mission.deadline.eq(cursorDeadline)
                                        .and(mission.id.gt(cursor)))
                );
            }
        }

        List<Tuple> results = jpaQueryFactory
                .select(
                        mission.id,
                        mission.name,
                        mission.description,
                        mission.deadline,
                        mission.point,
                        store.name
                )
                .from(mission)
                .join(store).on(mission.store.id.eq(store.id))
                .where(predicate)
                .orderBy(mission.deadline.asc(), mission.id.asc())
                .limit(15)
                .fetch();

        List<HomeDTO.MissionDTO> missions = results.stream()
                .map(tuple -> {
                    Long missionId = tuple.get(mission.id);
                    LocalDate deadline = tuple.get(mission.deadline);
                    String cursorValue = deadline.toString() + "_" + missionId;

                    return new HomeDTO.MissionDTO(
                            missionId,
                            tuple.get(mission.name),
                            tuple.get(mission.description),
                            deadline,
                            tuple.get(mission.point),
                            tuple.get(store.name),
                            cursorValue
                    );
                })
                .collect(Collectors.toList());

        return new HomeDTO(regionName, successMission, missions);
    }
}
