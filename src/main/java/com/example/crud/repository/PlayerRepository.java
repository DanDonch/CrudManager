package com.example.crud.repository;

import com.example.crud.data.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {
    List<Player> findByNameContainingIgnoreCase(String partialName);

}
