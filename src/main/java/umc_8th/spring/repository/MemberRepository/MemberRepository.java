package umc_8th.spring.repository.MemberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_8th.spring.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
}
