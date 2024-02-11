package com.example.crud.util;

import com.example.crud.data.dto.TeamCreationDto;
import com.example.crud.data.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class TeamMapper {

    public Team toEntity(TeamCreationDto teamCreationDto) {
        Team team = new Team();
        team.setName(teamCreationDto.getName());
        team.setBank(teamCreationDto.getBank());
        team.setPlayers(new ArrayList<>());
        return team;
    }

    public Team updateEntity(Team existingTeam, TeamCreationDto updatedTeamDto) {
        if (updatedTeamDto.getName() != null) {
            existingTeam.setName(updatedTeamDto.getName());
        }
        if (updatedTeamDto.getBank() != null) {
            existingTeam.setBank(updatedTeamDto.getBank());
        }
        return existingTeam;
    }
}
