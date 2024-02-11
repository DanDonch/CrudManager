package com.example.crud.service;

import com.example.crud.data.dto.TeamCreationDto;
import com.example.crud.data.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<Team> findAll();

    Optional<Team> findById(String id);

    List<Team> findByName(String name);

    Team save(Team team);

    void update(String id, TeamCreationDto updatedTeam);

    boolean delete(String id);

    boolean existsById(String id);
}