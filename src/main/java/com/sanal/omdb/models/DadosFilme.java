package com.sanal.omdb.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosFilme(
    @JsonAlias("Type") String type,
    @JsonAlias("Title") String titulo,
    @JsonAlias("imdbRating") String avaliacao,
    @JsonAlias("Runtime") String duracao,
    @JsonAlias("Released") String dataLancamento,
    @JsonAlias("Plot") String sinapse
) {
} 
