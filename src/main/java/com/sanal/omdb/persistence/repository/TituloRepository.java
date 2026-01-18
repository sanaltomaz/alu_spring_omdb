package com.sanal.omdb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanal.omdb.persistence.entity.TituloEntity;

/**
 * Repository base para acesso a títulos persistidos.
 *
 * Responsabilidade:
 * - Fornecer acesso genérico a entidades derivadas de TituloEntity
 *
 * Observações:
 * - Não substitui repositories específicos (Filme, Série)
 * - Uso previsto para consultas genéricas ou extensões futuras
 */
public interface TituloRepository extends JpaRepository<TituloEntity, Long> {
}

