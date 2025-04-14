package umc_8th.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc_8th.spring.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Modifying
    @Query("DELETE FROM Review r WHERE r.member.id = :memberId")
    void deleteByMemberId(@Param("memberId") Long memberId);
}
