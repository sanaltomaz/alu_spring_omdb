package com.sanal.omdb.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
    @JsonAlias("Type") String type,
    @JsonAlias("Title") String titulo,
    @JsonAlias("totalSeasons") Integer temporadas,
    @JsonAlias("imdbRating") String avaliacao,
    @JsonAlias("Released") String dataLancamento 
) {   
    @Override
    public String toString() {
        return "Título: " + titulo + "\n" +
               "Total de Temporadas: " + temporadas + "\n" +
               "Avaliação IMDb: " + avaliacao + "\n";
    }
}
