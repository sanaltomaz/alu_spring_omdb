package com.sanal.omdb.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import com.sanal.omdb.dto.omdb.OmdbEpisodioDto;
import com.sanal.omdb.dto.omdb.OmdbFilmeDto;
import com.sanal.omdb.dto.omdb.OmdbSerieDto;
import com.sanal.omdb.models.*;

/**
 * Factory responsável por criar objetos de domínio {@link Titulo}
 * a partir de DTOs externos da OMDB.
 *
 * Papel desta classe:
 * - Converter dados externos (Strings, formatos instáveis)
 * - Tratar valores ausentes ou inválidos ("N/A")
 * - Garantir que o domínio receba apenas dados coerentes
 *
 * Esta classe é o ponto de fronteira entre:
 * - Integração externa (OMDB)
 * - Núcleo do domínio da aplicação
 *
 * Observação importante:
 * - Nenhuma lógica de negócio vive aqui
 * - Nenhuma chamada HTTP é feita aqui
 * - Nenhuma decisão de fluxo ocorre aqui
 */
public class TituloFactory {

    /**
     * Formato padrão utilizado pela OMDB para datas de lançamento.
     *
     * Exemplo: "16 Jul 2010"
     */
    private static final DateTimeFormatter OMDB_DATE_FORMAT =
        DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

    /**
     * Cria um {@link Titulo} do tipo FILME a partir do DTO da OMDB.
     *
     * Responsabilidade:
     * - Mapear dados externos para o domínio
     * - Converter duração, avaliação e data
     *
     * Observação:
     * - A sinopse pode ser fornecida já traduzida por serviços externos
     */
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

    /**
     * Cria um {@link Titulo} do tipo SÉRIE a partir do DTO da OMDB.
     *
     * Responsabilidade:
     * - Mapear metadados básicos da série
     * - Converter avaliação e data de lançamento
     *
     * Observação:
     * - Não carrega temporadas ou episódios
     */
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

    /**
     * Cria um {@link Titulo} do tipo EPISÓDIO a partir do DTO da OMDB.
     *
     * Responsabilidade:
     * - Converter apenas os dados relevantes do episódio
     *
     * Observação:
     * - Episódios não possuem data de lançamento tratada neste contexto
     */
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

    /**
     * Converte a avaliação da OMDB para Double.
     *
     * Regras:
     * - "N/A" ou null resultam em null
     * - Valores inválidos não geram exceção
     */
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

    /**
     * Converte a duração da OMDB para minutos.
     *
     * Exemplo de entrada: "148 min"
     *
     * Regras:
     * - "N/A" ou null resultam em null
     * - Falhas de conversão não interrompem o fluxo
     */
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

    /**
     * Converte a data de lançamento da OMDB para {@link LocalDate}.
     *
     * Regras:
     * - "N/A", vazio ou null resultam em null
     * - Datas inválidas são descartadas silenciosamente
     *
     * Observação:
     * - O domínio nunca recebe datas inválidas
     */
    private LocalDate parseData(String dataLancamento) {
        if (dataLancamento == null ||
            dataLancamento.isBlank() ||
            dataLancamento.equalsIgnoreCase("N/A")) {
            return null;
        }

        try {
            return LocalDate.parse(dataLancamento, OMDB_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
