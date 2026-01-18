package com.sanal.omdb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanal.omdb.persistence.entity.SerieEntity;

/**
 * Repository responsável pela persistência de séries.
 *
 * Responsabilidade:
 * - Persistir e recuperar entidades SerieEntity
 *
 * Observações:
 * - Não gerencia episódios
 * - Operações de relacionamento são tratadas pelo JPA
 */
public interface SerieRepository extends JpaRepository<SerieEntity, Long> {
}
