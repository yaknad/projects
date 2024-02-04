package com.example.intuittestyakovnadler.controllers;

import com.example.intuittestyakovnadler.entities.Player;
import com.example.intuittestyakovnadler.repository.IPlayersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.CheckForNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/api")
@RestController
public class PlayerController {

    final
    IPlayersRepository repository;

    public PlayerController(IPlayersRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAll() {

        try {
            return ResponseEntity.ok(repository.GetAll());
            // TODO: refactor the repo to return CompleteableFuture and handle it here
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // TODO: handle specific exception types and return http statuses accordingly (400 etc.)
    //    @GetMapping("/players")
    //    public CompletableFuture<ResponseEntity<List<Player>>> getAll() {
    //
    //    }

    @GetMapping("/players/{playerID}")
    public ResponseEntity<Player> getById(@PathVariable String playerID) {

        if(playerID == null || playerID.isBlank())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        try {
            // TODO: refactor the repo to return CompleteableFuture and handle it here
            var result  = repository.GetById(playerID);
            return result.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        // TODO: handle specific exception types and return http statuses accordingly (400 etc.)
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // TODO: handle specific exception types and return http statuses accordingly (400 etc.)
    //    @GetMapping("/players/{playerID}")
    //    public CompletableFuture<ResponseEntity<Player>> getById(@PathVariable String playerID) {
    //
    //    }
}
