package com.example.crud.service.impl;

import com.example.crud.data.dto.TeamCreationDto;
import com.example.crud.data.entity.Player;
import com.example.crud.data.entity.Team;
import com.example.crud.repository.TeamRepository;
import com.example.crud.service.TeamService;
import com.example.crud.util.TeamMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamServiceImpl(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> findById(String id) {
        return teamRepository.findById(id);
    }

    @Override
    public List<Team> findByName(String name) {
        return teamRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void update(String id, TeamCreationDto updatedTeamDto) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
        Team updatedTeam = teamMapper.updateEntity(existingTeam, updatedTeamDto);
        teamRepository.save(updatedTeam);
    }

    @Override
    public boolean delete(String id) {
        try {
            teamRepository.deleteById(id);
            return true;
        } catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean existsById(String id) {
        return teamRepository.existsById(id);
    }

}