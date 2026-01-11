package com.sanal.omdb.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import com.sanal.omdb.dto.omdb.OmdbEpisodioDto;
import com.sanal.omdb.dto.omdb.OmdbFilmeDto;
import com.sanal.omdb.dto.omdb.OmdbSerieDto;
import com.sanal.omdb.models.*;

public class TituloFactory {

    private static final DateTimeFormatter OMDB_DATE_FORMAT =
        DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

    public Titulo fromFilme(OmdbFilmeDto dados, String sinopseTraduzida) {
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

    public Titulo fromSerie(OmdbSerieDto dados, String sinopseTraduzida) {
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

    public Titulo fromEpisodio(OmdbEpisodioDto dados) {
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

    private Double parseAvaliacao(String avaliacao) {
        if (avaliacao == null || avaliacao.equalsIgnoreCase("N/A")) {
            return null;
        }

        try {
            return Double.valueOf(avaliacao);
        } catch (Exception e) {
            return null;
        }
    }

    private Double parseDuracao(String duracao) {
        if (duracao == null || duracao.equalsIgnoreCase("N/A")) {
            return null;
        }

        try {
            return Double.valueOf(duracao.replace(" min", "").trim());
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate parseData(String dataLancamento) {
        if (dataLancamento == null || dataLancamento.isBlank() || dataLancamento.equalsIgnoreCase("N/A")) {
            return null;
        }

        try {
            return LocalDate.parse(dataLancamento, OMDB_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
