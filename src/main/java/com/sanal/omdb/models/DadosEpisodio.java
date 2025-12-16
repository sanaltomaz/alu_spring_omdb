package com.sanal.omdb.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(
    @JsonAlias("Title") String titulo,
    @JsonAlias("Released") String lancamento,
    @JsonAlias("Episode") Integer numero,
    @JsonAlias("imdbRating") String avaliacao
) {
    
}
