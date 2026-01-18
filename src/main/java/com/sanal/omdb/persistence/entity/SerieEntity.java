package com.sanal.omdb.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * Entidade que representa uma série persistida no sistema.
 *
 * Especialização de TituloEntity.
 *
 * Responsabilidade:
 * - Armazenar apenas os metadados básicos da série
 *
 * Observações:
 * - Não contém episódios
 * - Não contém regras de negócio
 * - Relacionamentos serão adicionados futuramente
 */
@Entity
public class SerieEntity extends TituloEntity {

    @Column(name = "total_temporadas", nullable = false)
    private Integer totalTemporadas;

    /**
     * Construtor padrão exigido pelo JPA.
     */
    public SerieEntity() {
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }
}
