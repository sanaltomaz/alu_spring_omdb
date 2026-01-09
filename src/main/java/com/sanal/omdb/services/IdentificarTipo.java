package com.sanal.omdb.services;

import com.sanal.omdb.dto.omdb.DadosEpisodio;
import com.sanal.omdb.dto.omdb.DadosFilme;
import com.sanal.omdb.dto.omdb.DadosSerie;

public class IdentificarTipo {
    public Class<?> identificarTipo (String json) {
        if (json.contains("\"Type\":\"movie\"")) {
            return DadosFilme.class;
        } else if (json.contains("\"Type\":\"series\"")) {
            return DadosSerie.class;
        } else if (json.contains("\"Episode\":")) {
            return DadosEpisodio.class;
        }
        throw new IllegalArgumentException("Tipo desconhecido no JSON");
    }
}
