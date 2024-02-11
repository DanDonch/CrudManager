package com.example.crud.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@ToString
@Table(name = "teams")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private BigDecimal bank;
    @JsonIgnore
    @OneToMany(mappedBy = "team")
    private List<Player> players;
}

