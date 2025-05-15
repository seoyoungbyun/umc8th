package umc_8th.spring.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

public class MissionRequestDTO {

    @Getter
    public static class MissionJoinDto{
        @NotBlank
        private String name;
        @NotNull
        private Integer point;
        @NotNull
        private LocalDate deadline;

        private String description;
    }
}
