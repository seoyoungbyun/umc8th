package umc_8th.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc_8th.spring.apiPayload.code.status.ErrorStatus;
import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.service.MemberMissionService.MemberMissionQueryService;
import umc_8th.spring.validation.annotation.IsAlreadyChallenging;
import umc_8th.spring.web.dto.MemberRequestDTO;

@Component
@RequiredArgsConstructor
public class IsAlreadyChallengingValidator implements ConstraintValidator<IsAlreadyChallenging, MemberRequestDTO.MemberMissionJoinDto> {

    private final MemberMissionQueryService memberMissionQueryService;

    @Override
    public void initialize(IsAlreadyChallenging constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MemberRequestDTO.MemberMissionJoinDto request, ConstraintValidatorContext context) {
        boolean isValid = memberMissionQueryService.findMissionsForMemberByStatus(request.getMemberId(), request.getMissionId(), MissionStatus.CHALLENGING).isPresent();

        if (isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.MISSION_ALREADY_CHALLENGING.toString()).addConstraintViolation();
        }

        return !isValid;

    }
}
