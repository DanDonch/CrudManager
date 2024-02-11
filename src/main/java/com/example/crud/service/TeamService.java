package com.example.crud.service;

import com.example.crud.data.dto.TeamCreationDto;
import com.example.crud.data.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    Optional<Team> getById(String id);
    void save(Team team);
    void update(String id, TeamCreationDto updatedTeam);
    void delete(String id);
    List<Team> findAll();
    List<Team> findByName(String name); // Добавлен метод для поиска по имени
}