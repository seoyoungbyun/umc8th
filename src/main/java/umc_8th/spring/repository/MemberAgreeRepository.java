package umc_8th.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc_8th.spring.domain.mapping.MemberAgree;

public interface MemberAgreeRepository extends JpaRepository<MemberAgree, Long> {
    @Modifying
    @Query("DELETE FROM MemberAgree m WHERE m.member.id = :memberId")
    void deleteByMemberId(@Param("memberId") Long memberId);
}
