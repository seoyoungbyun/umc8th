package umc_8th.spring.converter;

import umc_8th.spring.domain.Mission;
import umc_8th.spring.domain.Store;
import umc_8th.spring.web.dto.MissionRequestDTO;
import umc_8th.spring.web.dto.MissionResponseDTO;

import java.time.LocalDateTime;

public class MissionConverter {

    public static MissionResponseDTO.MissionJoinResultDTO toJoinResultDTO(Mission mission){
        return MissionResponseDTO.MissionJoinResultDTO.builder()
                .missionId(mission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Mission toMission(MissionRequestDTO.MissionJoinDto request, Store store){

        return Mission.builder()
                .name(request.getName())
                .point(request.getPoint())
                .deadline(request.getDeadline())
                .description(request.getDescription())
                .store(store)
                .build();
    }
}
