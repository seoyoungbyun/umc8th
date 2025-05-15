package umc_8th.spring.converter;

import umc_8th.spring.domain.Member;
import umc_8th.spring.domain.Mission;
import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.domain.mapping.MemberMission;
import umc_8th.spring.web.dto.MemberRequestDTO;
import umc_8th.spring.web.dto.MemberResponseDTO;

import java.time.LocalDateTime;

public class MemberMissionConverter {

    public static MemberResponseDTO.MemberMissionJoinResultDTO toJoinResultDTO(MemberMission memberMission){
        return MemberResponseDTO.MemberMissionJoinResultDTO.builder()
                .memberMissionId(memberMission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MemberMission toMemberMission(MemberRequestDTO.MemberMissionJoinDto request, Member member, Mission mission){

        return MemberMission.builder()
                .member(member)
                .mission(mission)
                .status(MissionStatus.CHALLENGING)
                .build();
    }
}
