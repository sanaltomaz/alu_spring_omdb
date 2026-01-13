package com.sanal.omdb.persistence.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanal.omdb.dto.omdb.OmdbSerieCompletaDto;
import com.sanal.omdb.dto.omdb.OmdbTemporadaDto;
import com.sanal.omdb.persistence.entity.SerieEntity;
import com.sanal.omdb.persistence.mapper.EpisodioEntityMapper;
import com.sanal.omdb.persistence.repository.EpisodioRepository;

/**
 * Service responsável exclusivamente pela persistência de episódios.
 *
 * Responsabilidades:
 * - Persistir episódios associados a uma série existente
 * - Garantir vínculo correto entre episódio e série
 *
 * NÃO faz:
 * - Busca na OMDB
 * - Criação de séries
 * - Análises ou estatísticas
 * - Decisão se episódio deve ou não existir
 *
 * Observações:
 * - Série deve existir antes da persistência
 * - Métodos podem ser custosos (persistência em lote)
 */
@Service
public class EpisodioPersistenciaService {

    private final EpisodioRepository episodioRepository;
    private final EpisodioEntityMapper mapper;

    public EpisodioPersistenciaService(
        EpisodioRepository episodioRepository,
        EpisodioEntityMapper mapper
    ) {
        this.episodioRepository = episodioRepository;
        this.mapper = mapper;
    }

    /**
     * Persiste todos os episódios de uma temporada específica.
     *
     * Pré-condições:
     * - Série já persistida
     * - Temporada já carregada da OMDB
     *
     * @param serie série persistida
     * @param temporadaDto dados da temporada
     */
    @Transactional
    public void salvarEpisodiosTemporada(
        SerieEntity serie,
        OmdbTemporadaDto temporadaDto
    ) {
        // Implementação será feita em etapa posterior
    }

    /**
     * Persiste todos os episódios de uma série completa.
     *
     * Observação:
     * - Método potencialmente custoso
     *
     * @param serie série persistida
     * @param serieCompletaDto dados completos da série
     */
    @Transactional
    public void salvarTodosEpisodios(
        SerieEntity serie,
        OmdbSerieCompletaDto serieCompletaDto
    ) {
        // Implementação será feita em etapa posterior
    }
}
