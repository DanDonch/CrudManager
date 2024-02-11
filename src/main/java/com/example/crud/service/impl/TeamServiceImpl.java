package com.example.crud.service.impl;

import com.example.crud.data.dto.TeamCreationDto;
import com.example.crud.data.entity.Team;
import com.example.crud.repository.TeamRepository;
import com.example.crud.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Optional<Team> getById(String id) {
        return Optional.empty();
    }

    @Override
    public void save(Team team) {}

    @Override
    public void update(String id, TeamCreationDto updatedTeam) {}

    @Override
    public void delete(String id) {}

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public List<Team> findByName(String name) {
        return null;
    }
}