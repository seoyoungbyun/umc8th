package umc_8th.spring.repository.FoodCategoryRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc_8th.spring.domain.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}