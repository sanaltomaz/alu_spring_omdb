package com.sanal.omdb.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sanal.omdb.dto.omdb.OmdbEpisodioDto;
import com.sanal.omdb.dto.omdb.OmdbSerieDto;
import com.sanal.omdb.models.EstatisticasSerie;

/**
 * Service responsável por análises e consultas sobre séries e episódios.
 *
 * Responsabilidades:
 * - Ordenar episódios
 * - Filtrar por avaliação
 * - Calcular estatísticas
 *
 * NÃO faz:
 * - Chamada HTTP
 * - Conversão de JSON
 * - Impressão em console
 * - Leitura de input
 */
@Service
public class SerieAnaliseService {

    /**
     * Retorna todos os episódios da série.
     * A forma de obtenção (temporadas, cache, etc.)
     * é responsabilidade deste service.
     */
    public List<OmdbEpisodioDto> listarEpisodios(OmdbSerieDto serie) {
        // implementação virá da antiga classe Funcoes
        return List.of();
    }

    /**
     * Retorna os melhores episódios da série,
     * ordenados por avaliação (decrescente).
     */
    public List<OmdbEpisodioDto> melhoresEpisodios(OmdbSerieDto serie, int limite) {
        // implementação virá da antiga classe Funcoes
        return List.of();
    }

    /**
     * Retorna os piores episódios da série,
     * ordenados por avaliação (crescente).
     */
    public List<OmdbEpisodioDto> pioresEpisodios(OmdbSerieDto serie, int limite) {
        // implementação virá da antiga classe Funcoes
        return List.of();
    }

    /**
     * Calcula estatísticas gerais da série:
     * - média
     * - melhor episódio
     * - pior episódio
     */
    public EstatisticasSerie calcularEstatisticas(OmdbSerieDto serie) {
        // implementação virá da antiga classe Funcoes
        return null;
    }
}
