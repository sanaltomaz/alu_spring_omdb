package com.sanal.omdb.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sanal.omdb.dto.omdb.OmdbEpisodioDto;
import com.sanal.omdb.dto.omdb.OmdbSerieCompletaDto;
import com.sanal.omdb.dto.omdb.OmdbSerieDto;

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
    
    private final TituloService tituloService;

    public SerieAnaliseService(TituloService tituloService) {
        this.tituloService = tituloService;
    }
    /**
     * Retorna todos os episódios da série.
     * A forma de obtenção (temporadas, cache, etc.)
     * é responsabilidade deste service.
     */
    private OmdbSerieCompletaDto carregarSerieCompleta(String nome) {
        return tituloService.buscarSerieComEpisodios(nome);
    }

    public OmdbSerieCompletaDto listarTodosEpisodios(String nome) {
        return carregarSerieCompleta(nome);
    }

    /**
     * Retorna os melhores episódios da série,
     * ordenados por avaliação (decrescente).
     */
    public List<OmdbEpisodioDto> melhoresEpisodios(OmdbSerieDto serie, int limite) {

        OmdbSerieCompletaDto serieCompleta = carregarSerieCompleta(serie.titulo());

        List<OmdbEpisodioDto> todosEpisodios = serieCompleta.temporadas().stream()
            .flatMap(t -> t.episodios().stream())
            .toList();

        return todosEpisodios.stream()
            .filter(e -> !e.avaliacao().equals("N/A"))
            .sorted((e1, e2) -> Double.compare(
                Double.parseDouble(e2.avaliacao()),
                Double.parseDouble(e1.avaliacao())
            ))
            .limit(limite)
            .collect(Collectors.toList());
    }

    /**
     * Retorna os piores episódios da série,
     * ordenados por avaliação (crescente).
     */
    public List<OmdbEpisodioDto> pioresEpisodios(OmdbSerieDto serie, int limite) {

        OmdbSerieCompletaDto serieCompleta = carregarSerieCompleta(serie.titulo());

        List<OmdbEpisodioDto> todosEpisodios = serieCompleta.temporadas().stream()
            .flatMap(t -> t.episodios().stream())
            .toList();

        return todosEpisodios.stream()
            .filter(e -> !e.avaliacao().equals("N/A"))
            .sorted((e1, e2) -> Double.compare(
                Double.parseDouble(e1.avaliacao()),
                Double.parseDouble(e2.avaliacao())
            ))
            .limit(limite)
            .collect(Collectors.toList());
    }
}
