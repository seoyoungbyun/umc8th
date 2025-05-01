package umc_8th.spring.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import umc_8th.spring.domain.enums.MissionStatus;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MissionByStatusDTO {
    private Long id;
    private String missionName;
    private String description;
    private Integer point;
    private MissionStatus status;
    private String storeName;
}
