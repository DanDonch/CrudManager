package com.example.crud.service;

import com.example.crud.data.dto.PlayerCreationDto;
import com.example.crud.data.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<Player> findAll();

    Optional<Player> findById(String id);

    List<Player> findByName(String name);

    Player save(Player player);

    void update(String id, PlayerCreationDto updatedPlayer);

    boolean delete(String id);

    boolean existsById(String id);
}

