package com.example.crud.service;

import com.example.crud.data.dto.PlayerCreationDto;
import com.example.crud.data.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    Optional<Player> findById(String id);
    void save(Player player);
    void update(String id, PlayerCreationDto updatedPlayer);
    boolean existsById(String id);
    boolean delete(String id);
    List<Player> findAll();
    List<Player> findByName(String name);
}

