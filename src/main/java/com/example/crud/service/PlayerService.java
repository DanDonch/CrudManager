package com.example.crud.service;

import com.example.crud.data.dto.PlayerCreationDto;
import com.example.crud.data.entity.Player;

import java.util.List;

public interface PlayerService {
    List<Player> findAll();

    Player findById(String id);

    List<Player> findByName(String name);

    Player save(Player player);

    void update(String id, PlayerCreationDto updatedPlayer);

    boolean delete(String id);

    boolean existsById(String id);
}

