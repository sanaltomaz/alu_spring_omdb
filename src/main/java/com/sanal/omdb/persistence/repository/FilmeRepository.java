package com.sanal.omdb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanal.omdb.persistence.entity.FilmeEntity;

/**
 * Repository responsável pela persistência de filmes.
 *
 * Responsabilidade:
 * - Persistir e recuperar entidades FilmeEntity
 *
 * Observações:
 * - Não contém regras de negócio
 * - Utiliza apenas operações padrão do Spring Data JPA
 */
public interface FilmeRepository extends JpaRepository<FilmeEntity, Long> {
}