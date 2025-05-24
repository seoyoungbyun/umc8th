package umc_8th.spring.converter;

import org.springframework.data.domain.Page;
import umc_8th.spring.domain.Mission;
import umc_8th.spring.domain.Store;
import umc_8th.spring.web.dto.MissionRequestDTO;
import umc_8th.spring.web.dto.MissionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static MissionResponseDTO.MissionPreViewDTO missionPreViewDTO(Mission mission){
        return MissionResponseDTO.MissionPreViewDTO.builder()
                .storeName(mission.getStore().getName())
                .point(mission.getPoint())
                .description(mission.getDescription())
                .createdAt(mission.getCreatedAt().toLocalDate())
                .deadline(mission.getDeadline())
                .build();
    }
    public static MissionResponseDTO.MissionPreViewListDTO missionPreViewListDTO(Page<Mission> missionList){

        List<MissionResponseDTO.MissionPreViewDTO> missionPreViewDTOList = missionList.stream()
                .map(MissionConverter::missionPreViewDTO).collect(Collectors.toList());

        return MissionResponseDTO.MissionPreViewListDTO.builder()
                .isLast(missionList.isLast())
                .isFirst(missionList.isFirst())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(missionPreViewDTOList.size())
                .missionList(missionPreViewDTOList)
                .build();
    }
}
