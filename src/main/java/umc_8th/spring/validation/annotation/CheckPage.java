package umc_8th.spring.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import umc_8th.spring.validation.validator.PageCheckValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PageCheckValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPage {

    String message() default "잘못된 page입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
