package umc_8th.spring.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import umc_8th.spring.domain.enums.Role;
import umc_8th.spring.validation.annotation.ExistCategories;

import java.util.Date;
import java.util.List;

public class MemberRequestDTO {

    @Getter
    public static class JoinDto{
        @NotBlank
        String name;
        @Email
        String email;    // 이메일 필드 추가
        @NotBlank
        String password;    // 비밀번호 필드 추가
        @NotNull
        Integer gender;
        @NotNull
        Date birth;
        @Size(min = 5, max = 12)
        String address;
        @Size(min = 5, max = 12)
        String specAddress;
        @ExistCategories
        List<Long> preferCategory;
        @NotNull
        Role role;    // 역할 필드 추가
    }

    @Getter
    @Setter
    public static class LoginRequestDTO{
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        private String email;

        @NotBlank(message = "패스워드는 필수입니다.")
        private String password;
    }

    @Getter
    public static class MemberMissionJoinDto{
        @NotNull
        Long memberId;
        @NotNull
        Long missionId;
    }
}
