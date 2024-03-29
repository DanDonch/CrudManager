package com.example.crud.controller;

import com.example.crud.aop.annotation.Authorized;
import com.example.crud.data.dto.PlayerCreationDto;
import com.example.crud.data.dto.RestResponse;
import com.example.crud.data.entity.Player;
import com.example.crud.repository.PlayerRepository;
import com.example.crud.service.PlayerService;
import com.example.crud.util.PlayerMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {
    private final Logger logger = LoggerFactory.getLogger(PlayerController.class);
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;
    @Value("${security.auth.token}")
    private String authToken;

    @GetMapping
    @Authorized
    public ResponseEntity<List<Player>> findAll() {
        try {
            List<Player> players = playerService.findAll();
            return ResponseEntity.ok(players);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Authorized
    public ResponseEntity<Player> getById(@PathVariable String id) {
        try {
            Optional<Player> optionalPlayer = Optional.ofNullable(playerService.findById(id));
            return optionalPlayer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search/{name}")
    @Authorized
    public ResponseEntity<List<Player>> findProductByName(@PathVariable String name) {
        try {
            List<Player> players = playerService.findByName(name);
            return ResponseEntity.ok(players);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    @Authorized
    public ResponseEntity<List<Player>> getByName(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam int page) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Player> playerPage = playerRepository.findAll(pageable);
            List<Player> players = playerPage.getContent();
            return ResponseEntity.ok(players);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Authorized
    public ResponseEntity<Player> save(@RequestBody PlayerCreationDto playerDto) {
        logger.info("Имя игрока " + playerDto.getName());
        logger.info("дата рождения " + playerDto.getBirthDate());
        logger.info("команда " + playerDto.getTeamId());
        try {
            Player player = playerMapper.toEntity(playerDto);
            Player createdPlayer = playerService.save(player);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdPlayer.getId())
                    .toUri();
            return ResponseEntity.created(location).body(createdPlayer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Authorized
    public ResponseEntity<Player> update(@PathVariable String id, @RequestBody PlayerCreationDto updatedPlayer) {
        try {
            if (!playerService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            playerService.update(id, updatedPlayer);
            return ResponseEntity.ok(playerService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Authorized
    public ResponseEntity<RestResponse> delete(@PathVariable String id) {
        try {
            boolean deleted = playerService.delete(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RestResponse("Error deleting player"));
        }
    }
}