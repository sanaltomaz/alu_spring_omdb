package com.sanal.omdb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanal.omdb.persistence.entity.FilmeEntity;

/**
 * Repository responsável pela persistência de filmes.
 *
 * Responsabilidade:
 * - Persistir e recuperar entidades FilmeEntity
 *
 * NÃO faz:
 * - Conversão entre domínio e entidade
 * - Regras de negócio
 * - Integração com APIs externas
 *
 * Observação:
 * - Utiliza as operações padrão do JpaRepository
 */
public interface FilmeRepository extends JpaRepository<FilmeEntity, Long> {
}
