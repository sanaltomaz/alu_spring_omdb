package com.sanal.omdb.dto.omdb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(
    @JsonAlias("Season") Integer numeroTemporada,
    @JsonAlias("Episodes") List<DadosEpisodio> episodios
) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Temporada ").append(numeroTemporada).append(":\n");
        for (DadosEpisodio episodio : episodios) {
            sb.append("  Episódio ").append(episodio.episodio())
              .append(": ").append(episodio.titulo())
              .append(" (Avaliação: ").append(episodio.avaliacao()).append(")\n");
        }
        return sb.toString();
     }
}
