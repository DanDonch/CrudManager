package com.example.crud.util;

import com.example.crud.data.dto.PlayerCreationDto;
import com.example.crud.data.entity.Player;
import com.example.crud.data.entity.Team;
import com.example.crud.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerMapper {
    private final TeamRepository teamRepository;

    public Player toEntity(PlayerCreationDto playerCreationDto) {
        Player player = new Player();
        player.setName(playerCreationDto.getName());
        player.setBirthDate(playerCreationDto.getBirthDate());
        player.setTeam(teamRepository.findById(playerCreationDto.getTeamId()).orElse(null));
        return player;
    }
    public Player updateEntity(Player existingPlayer, PlayerCreationDto updatedPlayerDto) {
        if (updatedPlayerDto.getName() != null) {
            existingPlayer.setName(updatedPlayerDto.getName());
        }
        if (updatedPlayerDto.getBirthDate() != null) {
            existingPlayer.setBirthDate(updatedPlayerDto.getBirthDate());
        }
        if (updatedPlayerDto.getTeamId() != null) {
            Team updatedTeam = teamRepository.findById(updatedPlayerDto.getTeamId()).orElse(null);
            existingPlayer.setTeam(updatedTeam);
        }

        return existingPlayer;
    }
}
