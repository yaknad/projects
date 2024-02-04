package com.example.intuittestyakovnadler.controllers;

import com.example.intuittestyakovnadler.entities.Player;
import com.example.intuittestyakovnadler.repository.IPlayersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PlayerControllerTests {

    @Mock
    IPlayersRepository repository;
    PlayerController sut;

    @BeforeEach
    void Init() {
        sut = new PlayerController(repository);
    }

    @Test
    public void whenRepositoryHasPlayersReturnThemAll() throws IOException {
        List<Player> players = Arrays.asList(new Player("id", 1, 1,1,"A", "B", "C",
                2, 2, 2, "U", "V", "W", "X", "Y",
                "Z", 7, 6, 'R', 'R', LocalDate.now(), LocalDate.now(), "G", "H"));
        Mockito.when(repository.GetAll()).thenReturn(players);
        var result = sut.getAll();
        assertEquals(200, result.getStatusCode().value());
        Mockito.verify(repository).GetAll();
    }

    @Test
    public void whenRepositoryThrowsExceptionReturn500() throws IOException {
        Mockito.when(repository.GetAll()).thenThrow(new IOException());
        var result = sut.getAll();
        assertEquals(500, result.getStatusCode().value());
        Mockito.verify(repository, times(1)).GetAll();
    }

    // TODO: add more unit tests coverage (for other cases in the api class and also for the playersRepository)

}
