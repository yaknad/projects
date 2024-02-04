package com.example.intuittestyakovnadler.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record Player (String playerID,
                      // used Integer to support null.
                      // I could have used "Short" instead of Integer, but in order to shorten dev time,
                      // I wanted to use Guava's "tryParse" which doesn't have tryParse implementation for Shorts.
                      // In real life, I would implement it myself and use "Short" here.
                      Integer birthYear,
                      Integer birthMonth,
                      Integer birthDay,
                      String birthCountry,
                      String birthState,
                      String birthCity,
                      Integer deathYear,
                      Integer deathMonth,
                      Integer deathDay,
                      String deathCountry,
                      String deathState,
                      String deathCity,
                      String nameFirst,
                      String nameLast,
                      String nameGiven,
                      Integer weight,
                      Integer height,
                      Character bats,
                      // when I create a separate DTO (see README), I can better control APIs return result properties naming and ordering
                      @JsonProperty(value = "throws")
                      Character throwz,
                      LocalDate debut,
                      LocalDate finalGame,
                      String retroID,
                      String bbrefID) {}



