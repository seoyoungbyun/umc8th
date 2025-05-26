package umc_8th.spring.repository.MemberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_8th.spring.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);
}
