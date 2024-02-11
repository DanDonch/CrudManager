package com.example.crud.controller;

import com.example.crud.data.dto.RestResponse;
import com.example.crud.data.dto.TeamCreationDto;
import com.example.crud.data.entity.Team;
import com.example.crud.repository.TeamRepository;
import com.example.crud.service.TeamService;
import com.example.crud.util.TeamMapper;
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
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final Logger logger = LoggerFactory.getLogger(TeamController.class);
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final TeamMapper teamMapper;
    @Value("${security.auth.token}")
    private String authToken;

    @GetMapping
    public ResponseEntity<List<Team>> findAll() {
        try {
            List<Team> teams = teamService.findAll();
            return ResponseEntity.ok(teams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getById(@RequestHeader("X-Auth-Token") String token, @PathVariable String id) {
        try {
            Optional<Team> optionalTeam = teamService.findById(id);
            return optionalTeam.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Team>> findTeamByName(@PathVariable String name) {
        try {
            List<Team> teams = teamService.findByName(name);
            return ResponseEntity.ok(teams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Team>> getByName(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam int page) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Team> teamPage = teamRepository.findAll(pageable);
            List<Team> teams = teamPage.getContent();
            return ResponseEntity.ok(teams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Team> save(@RequestBody TeamCreationDto teamDto) {
        try {
            Team team = teamMapper.toEntity(teamDto);
            Team createdTeam = teamRepository.save(team);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdTeam.getId())
                    .toUri();
            return ResponseEntity.created(location).body(createdTeam);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> update(@PathVariable String id, @RequestBody TeamCreationDto updatedTeam) {
        try {
            if (!teamService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            teamService.update(id, updatedTeam);
            return ResponseEntity.ok(teamService.findById(id).orElseThrow(EntityNotFoundException::new));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse> delete(@PathVariable String id) {
        try {
            boolean deleted = teamService.delete(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RestResponse("Error deleting team"));
        }
    }
}