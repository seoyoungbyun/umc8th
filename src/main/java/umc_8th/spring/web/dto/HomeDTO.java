package umc_8th.spring.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HomeDTO {
    private String regionName;
    private Long successMission;
    private List<MissionDTO> missions;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MissionDTO{
        private Long missionId;
        private String missionName;
        private String description;
        private LocalDate deadline;
        private Integer point;
        private String storeName;
        private String cursor;
    }
}
