package com.sanal.omdb.persistence.entity;

import jakarta.persistence.*;

/**
 * Entidade que representa um episódio de uma série.
 *
 * Episódios pertencem a uma única série e não existem de forma independente
 * no domínio principal do sistema.
 *
 * Decisões de modelagem:
 * - Episódio NÃO herda de TituloEntity
 * - Episódios são sempre vinculados a uma Série
 * - Temporada é representada como número inteiro
 *
 * Observações:
 * - Esta entidade não contém lógica de negócio
 * - Persistência ocorre exclusivamente via services
 */
@Entity
@Table(name = "episodio_entity")
public class EpisodioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "numero_episodio", nullable = false)
    private Integer numeroEpisodio;

    @Column(name = "numero_temporada", nullable = false)    
    private Integer numeroTemporada;

    private Double avaliacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "serie_id", nullable = false)
    private SerieEntity serie;

    public EpisodioEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public Integer getNumeroTemporada() {
        return numeroTemporada;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public SerieEntity getSerie() {
        return serie;
    }

    /* Setters necessários para mapeamento */

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public void setNumeroTemporada(Integer numeroTemporada) {
        this.numeroTemporada = numeroTemporada;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public void setSerie(SerieEntity serie) {
        this.serie = serie;
    }
}
