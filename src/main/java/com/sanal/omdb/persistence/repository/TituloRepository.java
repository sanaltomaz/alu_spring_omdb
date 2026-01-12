package com.sanal.omdb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanal.omdb.persistence.entity.TituloEntity;

/**
 * Repository base para consultas genéricas de títulos.
 *
 * Responsabilidade:
 * - Oferecer acesso comum a entidades derivadas de TituloEntity
 *
 * Observação:
 * - Não substitui repositories específicos (Filme, Série, Episódio)
 * - Uso principal: consultas genéricas ou futuras extensões
 */
public interface TituloRepository extends JpaRepository<TituloEntity, Long> {
}
