package com.example.crud.repository;

import com.example.crud.data.entity.Player;
import com.example.crud.data.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, String> {
    List<Team> findByNameContainingIgnoreCase(String partialName);
}

