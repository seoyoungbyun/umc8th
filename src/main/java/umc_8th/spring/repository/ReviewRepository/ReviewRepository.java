package umc_8th.spring.repository.ReviewRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_8th.spring.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}
