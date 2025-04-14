package umc_8th.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc_8th.spring.domain.mapping.MemberMission;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    @Modifying
    @Query("DELETE FROM MemberMission m WHERE m.member.id = :memberId")
    void deleteByMemberId(@Param("memberId") Long memberId);
}
