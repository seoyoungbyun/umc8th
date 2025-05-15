package umc_8th.spring.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class StoreRequestDTO {

    @Getter
    public static class StoreJoinDto{
        @NotBlank
        String name;
        @NotBlank
        @Size(min = 5, max = 50)
        String address;
        @NotNull
        Long regionId;
    }
}
