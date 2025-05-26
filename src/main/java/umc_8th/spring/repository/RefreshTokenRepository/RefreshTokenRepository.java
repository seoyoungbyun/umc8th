package umc_8th.spring.repository.RefreshTokenRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_8th.spring.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByEmail(String email);
}

