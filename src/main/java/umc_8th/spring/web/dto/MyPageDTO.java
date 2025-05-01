package umc_8th.spring.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyPageDTO {
    private Long memberId;
    private String profileImage;
    private String name;
    private String email;
    private Boolean phoneAuth;
    private String phoneNum;
    private Long points;
}
