package com.example.crud.data.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerCreationDto {
    private String name;
    private LocalDate birthDate;
    private String teamId;


}