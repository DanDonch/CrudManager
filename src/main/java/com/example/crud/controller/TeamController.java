package com.example.crud.controller;

import com.example.crud.data.dto.RestResponse;
import com.example.crud.data.dto.TeamCreationDto;
import com.example.crud.data.entity.Team;
import com.example.crud.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final Logger logger = LoggerFactory.getLogger(TeamController.class);
    private final TeamService teamService;
    @Value("${security.auth.token}")
    private String authToken;

    @GetMapping
    public List<Team> findAll() {
        List<Team> teams = teamService.findAll();
        logger.info("ВСЕГО КОМАНД " + teams.size());
        return teams;
    }

    @GetMapping("{id}")
    public RestResponse getById(@PathVariable String id) {
        return new RestResponse(teamService.getById(id));
    }

    @GetMapping("/search")
    public RestResponse searchByName(@RequestParam String name) {
        List<Team> teams = teamService.findByName(name);
        return new RestResponse(teams);
    }
    @PostMapping
    public RestResponse create(@RequestBody TeamCreationDto teamDto) {
        //teamService.save(teamDto);
        return new RestResponse("Team created");
    }

    @PutMapping("{id}")
    public RestResponse update(@PathVariable String id, @RequestBody TeamCreationDto updatedTeam) {
        teamService.update(id, updatedTeam);
        return new RestResponse("Team updated");
    }

    @DeleteMapping("{id}")
    public RestResponse delete(@PathVariable String id) {
        teamService.delete(id);
        return new RestResponse("Team deleted");
    }
}
