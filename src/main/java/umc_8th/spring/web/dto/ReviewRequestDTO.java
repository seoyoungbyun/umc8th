package umc_8th.spring.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class ReviewRequestDTO {

    @Getter
    public static class ReviewJoinDto{
        @NotBlank
        @Size(min = 1, max = 100)
        String content;
        @NotNull
        Double rating;
    }
}
