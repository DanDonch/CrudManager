package com.example.crud.service.impl;

import com.example.crud.data.dto.PlayerCreationDto;
import com.example.crud.data.entity.Player;
import com.example.crud.repository.PlayerRepository;
import com.example.crud.service.PlayerService;
import com.example.crud.util.PlayerMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> findById(String id) {
        return playerRepository.findById(id);
    }

    @Override
    public List<Player> findByName(String name) {
        return playerRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public void update(String id, PlayerCreationDto updatedPlayerDto) {
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));
        Player updatedPlayer = playerMapper.updateEntity(existingPlayer, updatedPlayerDto);
        playerRepository.save(updatedPlayer);
    }

    @Override
    public boolean delete(String id) {
        try {
            playerRepository.deleteById(id);
            return true;
        } catch (IllegalArgumentException | EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean existsById(String id) {
        return playerRepository.existsById(id);
    }
}