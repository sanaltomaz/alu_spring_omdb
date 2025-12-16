package com.sanal.omdb.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
    @JsonAlias("Type") String type,
    @JsonAlias("Title") String titulo,
    @JsonAlias("Year") String lancamento,
    @JsonAlias("totalSeasons") String temporadas,
    @JsonAlias("imdbRating") String notaImdb ) {   
}
