package com.example.intuittestyakovnadler.repository;

import com.example.intuittestyakovnadler.entities.Player;
import com.google.common.primitives.Ints;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
public class PlayersRepository implements IPlayersRepository {

    @Value("classpath:static/files/player.csv")
    private Resource resource;
    // to keep things simple, I used this in memory collection instead of a DB
    private HashMap<String, Player> playersDB;
    private List<Player> allPlayersCache;

    @PostConstruct
    public void Init() throws IOException{
        playersDB = new HashMap<>();
        readFileIntoInMemoryDB();
    }

    private void readFileIntoInMemoryDB() throws IOException {

        try(Scanner scanner = new Scanner(resource.getFile())) {
            // ***** I've used scanner instead of reading the whole file in one batch in order to be able to handle
            // very large files that may require large amount of memory

            // skipping the headers line (assuming the files always contains it)
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                var player = convertLineToRecord(scanner.nextLine());
                if(player.isEmpty()) continue;
                var playerInstance = player.get();
                // assuming there are no Id duplications (see README file)
                playersDB.putIfAbsent(playerInstance.playerID(), playerInstance);
            }

        } catch (IOException e) {
            // TODO: log error
            throw e;
        }
    }

    private Optional<Player> convertLineToRecord(String line){

        if(line.isBlank() || line.isEmpty()) return Optional.empty();

        var lineCells = line.split(",");
        // skip lines with no playerId
        if(lineCells[0].isBlank()) return Optional.empty();

        try {
            return Optional.of(new Player(lineCells[0],
                    Ints.tryParse(getCellStrValue(lineCells, 1, false)),
                    Ints.tryParse(getCellStrValue(lineCells, 2, false)),
                    Ints.tryParse(getCellStrValue(lineCells, 3, false)),
                    getCellStrValue(lineCells, 4, false),
                    getCellStrValue(lineCells, 5, false),
                    getCellStrValue(lineCells, 6, false),
                    Ints.tryParse(getCellStrValue(lineCells, 7, false)),
                    Ints.tryParse(getCellStrValue(lineCells, 8, false)),
                    Ints.tryParse(getCellStrValue(lineCells, 9, false)),
                    getCellStrValue(lineCells, 10, false),
                    getCellStrValue(lineCells, 11, false),
                    getCellStrValue(lineCells, 12, false),
                    getCellStrValue(lineCells, 13, false),
                    getCellStrValue(lineCells, 14, false),
                    getCellStrValue(lineCells, 15, false),
                    Ints.tryParse(getCellStrValue(lineCells, 16, false)),
                    Ints.tryParse(getCellStrValue(lineCells, 17, false)),
                    getCellStrValue(lineCells, 18, true).charAt(0),
                    getCellStrValue(lineCells, 19, true).charAt(0),
                    parseDate(getCellStrValue(lineCells, 20, false)).orElse(null),
                    parseDate(getCellStrValue(lineCells, 21, false)).orElse(null),
                    getCellStrValue(lineCells, 22, false),
                    getCellStrValue(lineCells, 23, false)));
        }
        catch (Exception e){
            // TODO: log error but continue to read the rest of the file for valid data
            return Optional.empty();
        }
    }

    private String getCellStrValue(String[] lineCells, int index, boolean mustBeNonEmpty) {

        if(lineCells.length < (index + 1))
            return mustBeNonEmpty ? " " : "";
        var value = lineCells[index];
        return !mustBeNonEmpty ? lineCells[index].trim()
                               : lineCells[index].isEmpty() ? " "
                                                            : lineCells[index];
    }

    private Optional<LocalDate> parseDate(String date) {

        try {
            return Optional.of(LocalDate.parse(date));
        }
        catch(DateTimeParseException e) {
            return Optional.empty();
        }
    }

    // throws IOException was added to mimic a DB connection
    @Override
    public List<Player> GetAll() throws IOException {

        return Optional.ofNullable(allPlayersCache).orElseGet(() -> {
            allPlayersCache = playersDB.values().stream().collect(Collectors.toUnmodifiableList());
            return allPlayersCache;
        });
    }

    //TODO: mimic an actual DB call
    //    @Override
    //    public CompletableFuture<List<Player>> GetAll() throws IOException {
    //
    //        // used future to mimic an actual network call to the DB
    //        return CompletableFuture.supplyAsync(() ->
    //                Optional.ofNullable(allPlayersCache).orElseGet(() -> {
    //                    allPlayersCache = playersDB.values().stream().collect(Collectors.toUnmodifiableList());
    //                    return allPlayersCache;
    //                }));
    //    }



    @Override
    public Optional<Player> GetById(String id) {
        return Optional.of(playersDB.getOrDefault(id, null));
    }

    //TODO: mimic an actual DB call
    //    @Override
    //    public CompletableFuture<Optional<Player>> GetById(String id) {
    //
    //        // used future to mimic an actual network call to the DB
    //        return CompletableFuture.supplyAsync(() ->
    //                Optional.of(playersDB.getOrDefault(id, null)));
    //    }
}



