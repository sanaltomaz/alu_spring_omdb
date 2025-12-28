package com.sanal.omdb.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
    @JsonAlias("Type") String type,
    @JsonAlias("Title") String titulo,
    @JsonAlias("totalSeasons") Integer temporadas,
    @JsonAlias("imdbRating") String avaliacao,
    @JsonAlias("Released") String dataLancamento,
    @JsonAlias("Plot") String sinapse
) {  
} 
