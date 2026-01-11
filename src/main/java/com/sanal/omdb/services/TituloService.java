package com.sanal.omdb.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sanal.omdb.dto.omdb.OmdbSerieCompletaDto;
import com.sanal.omdb.dto.omdb.OmdbSerieDto;
import com.sanal.omdb.dto.omdb.OmdbTemporadaDto;
import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.omdb.OmdbClient;

/**
 * Service responsável por orquestrar a obtenção de títulos.
 *
 * Papel desta classe:
 * - Coordenar chamadas ao OmdbClient
 * - Decidir quais dados externos devem ser buscados
 * - Converter dados externos (DTOs) em objetos de domínio
 *
 * O que este service NÃO faz:
 * - Não realiza chamadas HTTP diretamente
 * - Não converte JSON manualmente
 * - Não executa lógica de análise ou estatísticas
 * - Não imprime dados nem interage com o usuário
 *
 * Observação:
 * Este service representa o ponto central entre integração externa
 * (OMDB) e o domínio da aplicação.
 */
@Service
public class TituloService {

    private final OmdbClient omdbClient;
    private final TituloFactory tituloFactory = new TituloFactory();

    public TituloService(OmdbClient omdbClient) {
        this.omdbClient = omdbClient;
    }

    /**
     * Busca um título genérico pelo nome e retorna o objeto de domínio correspondente.
     *
     * Responsabilidade:
     * - Delegar a busca de dados externos ao OmdbClient
     * - Identificar o tipo técnico retornado (filme, série ou episódio)
     * - Criar o objeto de domínio apropriado através da factory
     *
     * Observações:
     * - O uso de instanceof é intencional e temporário
     * - A lógica de decisão fica centralizada neste service
     * - O restante da aplicação não conhece DTOs externos
     */
    public Titulo buscarPorNome(String nome) {
        Object dados = omdbClient.buscarTitulo(nome);

        if (dados instanceof com.sanal.omdb.dto.omdb.OmdbFilmeDto) {
            return tituloFactory.fromFilme(
                (com.sanal.omdb.dto.omdb.OmdbFilmeDto) dados,
                null
            );
        }

        if (dados instanceof com.sanal.omdb.dto.omdb.OmdbSerieDto) {
            return tituloFactory.fromSerie(
                (com.sanal.omdb.dto.omdb.OmdbSerieDto) dados,
                null
            );
        }

        return null;
    }

    /**
     * Busca apenas os metadados básicos de uma série.
     *
     * Responsabilidade:
     * - Delegar diretamente a consulta ao OmdbClient
     *
     * Observação:
     * - Não busca temporadas nem episódios
     * - Método leve, útil para consultas simples
     */
    public OmdbSerieDto buscarSerie(String nome) {
        return omdbClient.buscarSerie(nome);
    }

    /**
     * Busca uma temporada específica de uma série.
     *
     * Responsabilidade:
     * - Encapsular a chamada ao OmdbClient
     * - Fornecer acesso pontual a uma temporada
     *
     * Observação:
     * - Não realiza agregação de dados
     * - Não conhece o contexto completo da série
     */
    public OmdbTemporadaDto buscarTemporada(String nomeSerie, int numeroTemporada) {
        return omdbClient.buscarTemporada(nomeSerie, numeroTemporada);
    }

    /**
     * Busca uma série completa com todas as suas temporadas e episódios.
     *
     * Responsabilidade:
     * - Orquestrar múltiplas chamadas à OMDB
     * - Agregar os dados da série e de suas temporadas
     * - Retornar um DTO agregado pronto para análise
     *
     * Observações:
     * - Este método pode ser custoso (múltiplas chamadas HTTP)
     * - Não realiza análise ou estatísticas
     * - Serve como base para o SerieAnaliseService
     */
    public OmdbSerieCompletaDto buscarSerieComEpisodios(String nome) {
        OmdbSerieDto serie = buscarSerie(nome);
        int totalTemporadas = serie.temporadas();

        List<OmdbTemporadaDto> temporadas = new ArrayList<>();

        for (int i = 1; i <= totalTemporadas; i++) {
            OmdbTemporadaDto temporadaDto = buscarTemporada(nome, i);
            temporadas.add(temporadaDto);
        }

        return new OmdbSerieCompletaDto(serie, temporadas);
    }
}
