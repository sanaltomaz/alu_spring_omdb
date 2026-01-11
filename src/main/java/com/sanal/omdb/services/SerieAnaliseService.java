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
 * Papel desta classe:
 * - Trabalhar exclusivamente com dados já obtidos da OMDB
 * - Aplicar regras de ordenação e filtragem
 * - Preparar listas de episódios para consumo externo
 *
 * Este service atua após a obtenção completa dos dados da série,
 * utilizando o {@link TituloService} como fonte.
 *
 * O que este service NÃO faz:
 * - Não realiza chamadas HTTP
 * - Não converte JSON
 * - Não cria objetos de domínio
 * - Não imprime dados nem interage com o usuário
 */
@Service
public class SerieAnaliseService {
    
    private final TituloService tituloService;

    public SerieAnaliseService(TituloService tituloService) {
        this.tituloService = tituloService;
    }

    /**
     * Carrega todos os dados de uma série, incluindo temporadas e episódios.
     *
     * Responsabilidade:
     * - Delegar ao TituloService a obtenção completa da série
     *
     * Observação:
     * - Método interno utilizado como base para análises
     * - Pode ser custoso (múltiplas chamadas externas)
     */
    private OmdbSerieCompletaDto carregarSerieCompleta(String nome) {
        return tituloService.buscarSerieComEpisodios(nome);
    }

    /**
     * Retorna todos os episódios da série, organizados por temporada.
     *
     * Observação:
     * - Não aplica filtros ou ordenações
     * - Útil para listagens completas
     */
    public OmdbSerieCompletaDto listarTodosEpisodios(String nome) {
        return carregarSerieCompleta(nome);
    }

    /**
     * Retorna os melhores episódios da série.
     *
     * Critérios:
     * - Episódios com avaliação diferente de "N/A"
     * - Ordenação por avaliação em ordem decrescente
     * - Limite máximo de resultados configurável
     *
     * Observação:
     * - Avaliações são tratadas como valores numéricos apenas neste ponto
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
     * Retorna os piores episódios da série.
     *
     * Critérios:
     * - Episódios com avaliação diferente de "N/A"
     * - Ordenação por avaliação em ordem crescente
     * - Limite máximo de resultados configurável
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
