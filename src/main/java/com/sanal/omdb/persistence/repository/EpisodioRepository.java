package com.sanal.omdb.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanal.omdb.persistence.entity.EpisodioEntity;

/**
 * Repository responsável pela persistência de episódios.
 *
 * Responsabilidade:
 * - Persistir e consultar episódios vinculados a uma série
 *
 * Consultas suportadas:
 * - Buscar episódios por série
 * - Buscar episódios por série e temporada
 *
 * Observação:
 * - Relacionamento com Série é definido na entidade
 * - Não contém lógica de agregação ou análise
 */
public interface EpisodioRepository extends JpaRepository<EpisodioEntity, Long> {
    
    List<EpisodioEntity> findBySerieId(Long serieId);
    
    List<EpisodioEntity> findBySerieIdAndNumeroTemporada(
        Long serieId,
        Integer numeroTemporada
    );
}
