package com.example.crud.data.dto;

import com.example.crud.data.entity.Player;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TeamCreationDto {
    private String id;
    private String name;
    private BigDecimal bank;
    private List<Player> players;
}

