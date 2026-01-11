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
 * Este service atua como a camada de aplicação entre:
 * - Integração externa (OMDB)
 * - Núcleo do domínio
 *
 * O que este service NÃO faz:
 * - Não realiza chamadas HTTP diretamente
 * - Não converte JSON manualmente
 * - Não executa lógica de análise ou estatísticas
 * - Não imprime dados nem interage com o usuário
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
     * - Identificar o tipo técnico retornado (filme ou série)
     * - Criar o objeto de domínio apropriado através da factory
     *
     * Observações:
     * - O uso de instanceof é intencional neste momento
     * - Evita que o domínio conheça DTOs externos
     * - Esta lógica poderá evoluir para um mecanismo mais explícito no futuro
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
     * Observações:
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
     * Observações:
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
     * - Agregar metadados da série e dados de todas as temporadas
     * - Retornar um DTO agregado pronto para consumo por serviços de análise
     *
     * Observações:
     * - Este método é custoso (uma chamada HTTP por temporada)
     * - Deve ser usado com cautela em fluxos interativos
     * - Lança exceção caso o título informado não seja uma série
     */
    public OmdbSerieCompletaDto buscarSerieComEpisodios(String nome) {
        OmdbSerieDto serie = buscarSerie(nome);

        if (serie == null || serie.temporadas() == null) {
            throw new IllegalStateException("Título não é uma série");
        }

        int totalTemporadas = serie.temporadas();
        List<OmdbTemporadaDto> temporadas = new ArrayList<>();

        for (int i = 1; i <= totalTemporadas; i++) {
            OmdbTemporadaDto temporadaDto = buscarTemporada(nome, i);
            temporadas.add(temporadaDto);
        }

        return new OmdbSerieCompletaDto(serie, temporadas);
    }
}
