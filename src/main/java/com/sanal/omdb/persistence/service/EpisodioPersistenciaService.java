package com.sanal.omdb.persistence.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanal.omdb.dto.omdb.OmdbSerieCompletaDto;
import com.sanal.omdb.dto.omdb.OmdbTemporadaDto;
import com.sanal.omdb.persistence.entity.SerieEntity;
import com.sanal.omdb.persistence.mapper.EpisodioEntityMapper;
import com.sanal.omdb.persistence.repository.EpisodioRepository;

/**
 * Service responsável pela persistência de episódios associados a séries.
 *
 * Responsabilidades:
 * - Persistir episódios vinculados a uma série já existente
 * - Garantir o relacionamento correto entre episódio e série
 *
 * NÃO faz:
 * - Busca de dados na OMDB
 * - Criação ou atualização de séries
 * - Decisão de fluxo ou regras de negócio
 * - Análises ou processamento estatístico
 *
 * Observações:
 * - A série deve estar previamente persistida
 * - A persistência pode envolver operações em lote e ser custosa
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
     * Observações:
     * - Operação atômica por temporada
     *
     * @param serie série persistida
     * @param temporadaDto dados da temporada
     */
    @Transactional
    void salvarEpisodiosTemporada(
        SerieEntity serie,
        OmdbTemporadaDto temporadaDto
    ) {
        Integer numeroTemporada = temporadaDto.numeroTemporada();

        if (temporadaDto.episodios() == null || temporadaDto.episodios().isEmpty()) {
            return;
        }

        temporadaDto.episodios().forEach(episodioDto -> {
            episodioRepository.save(
                mapper.toEntity(
                    episodioDto,
                    serie,
                    numeroTemporada
                )
            );
        });
    }

    /**
     * Persiste todos os episódios de uma série completa.
     *
     * Observações:
     * - Método potencialmente custoso
     * - Não é uma operação transacional única; falhas ocorrem por temporada
     *
     * @param serie série persistida
     * @param serieCompletaDto dados completos da série
     */
    public void salvarTodosEpisodios(
        SerieEntity serie,
        OmdbSerieCompletaDto serieCompletaDto
    ) {
        if (serieCompletaDto.temporadas() == null || serieCompletaDto.temporadas().isEmpty()) {
            return;
        }

        serieCompletaDto.temporadas().forEach(temporadaDto -> {
            salvarEpisodiosTemporada(serie, temporadaDto);
        });
    }
}
