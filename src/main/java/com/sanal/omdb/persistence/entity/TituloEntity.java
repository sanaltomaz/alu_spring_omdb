package com.sanal.omdb.persistence.entity;

import java.time.LocalDate;

import com.sanal.omdb.models.TipoTitulo;

import jakarta.persistence.*;

/**
 * Entidade base abstrata para títulos persistidos no sistema.
 *
 * Representa os atributos comuns entre filmes e séries no modelo relacional.
 * Esta entidade NÃO deve ser instanciada diretamente.
 *
 * Decisões de modelagem:
 * - Usa herança JPA no modo JOINED
 * - Cada especialização (Filme, Série) possui sua própria tabela
 * - Mantém separação clara entre domínio e persistência
 *
 * Observações:
 * - Esta entidade não contém regras de negócio
 * - Conversões de DTOs externos para entidade ocorrem em serviços/factories
 * - Campos nulos refletem dados opcionais vindos da API externa
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TituloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private Double avaliacao;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    private String sinopse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTitulo tipo;

    protected TituloEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public String getSinopse() {
        return sinopse;
    }

    public TipoTitulo getTipo() {
        return tipo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public void setTipo(TipoTitulo tipo) {
        this.tipo = tipo;
    }

    
}
