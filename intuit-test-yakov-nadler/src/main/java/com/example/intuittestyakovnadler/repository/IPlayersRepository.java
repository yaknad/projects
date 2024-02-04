package com.example.intuittestyakovnadler.repository;

import com.example.intuittestyakovnadler.entities.Player;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface IPlayersRepository {
    List<Player> GetAll() throws IOException;
    Optional<Player> GetById(String id);

//TODO: mimic an actual call to the DB:
//    CompletableFuture<List<Player>> GetAll() throws IOException;
//    CompletableFuture<Optional<Player>> GetById(String id);
}
