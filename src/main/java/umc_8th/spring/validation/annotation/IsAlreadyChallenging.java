package umc_8th.spring.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import umc_8th.spring.validation.validator.IsAlreadyChallengingValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsAlreadyChallengingValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsAlreadyChallenging {

    String message() default "이미 진행 중인 미션입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
