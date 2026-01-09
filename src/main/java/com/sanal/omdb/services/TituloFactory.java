package com.sanal.omdb.services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.sanal.omdb.dto.omdb.DadosEpisodio;
import com.sanal.omdb.dto.omdb.DadosFilme;
import com.sanal.omdb.dto.omdb.DadosSerie;
import com.sanal.omdb.models.*;

public class TituloFactory {

    public Titulo fromFilme(DadosFilme dados, String sinopseTraduzida) {
        return new Titulo(
                TipoTitulo.FILME,
                dados.titulo(),
                null,
                null,
                parseDuracao(dados.duracao()),
                parseAvaliacao(dados.avaliacao()),
                parseData(dados.dataLancamento()),
                sinopseTraduzida
        );
    }

    public Titulo fromSerie(DadosSerie dados, String sinopseTraduzida) {
        return new Titulo(
                TipoTitulo.SERIE,
                dados.titulo(),
                Integer.valueOf(dados.temporadas()),
                null,
                null,
                parseAvaliacao(dados.avaliacao()),
                parseData(dados.dataLancamento()),
                sinopseTraduzida
        );
    }

    public Titulo fromEpisodio(DadosEpisodio dados) {
        return new Titulo(
                TipoTitulo.EPISODIO,
                dados.titulo(),
                null,
                dados.episodio(),
                null,
                parseAvaliacao(dados.avaliacao()),
                null,
                null
        );
    }

    private Double parseAvaliacao(String valor) {
        try {
            return Double.valueOf(valor);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private Double parseDuracao(String valor) {
        try {
            return Double.valueOf(valor.replace(" min", ""));
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate parseData(String valor) {
        try {
            return LocalDate.parse(valor);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
