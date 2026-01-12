package com.sanal.omdb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanal.omdb.persistence.entity.SerieEntity;

/**
 * Repository responsável pela persistência de séries.
 *
 * Responsabilidade:
 * - Persistir e recuperar entidades SerieEntity
 *
 * Observação:
 * - Não lida diretamente com episódios
 * - Relacionamentos são resolvidos via JPA
 */
public interface SerieRepository extends JpaRepository<SerieEntity, Long> {
}
