package com.sanal.omdb.models;

import java.time.LocalDate;

public class Titulo {
    private String type;
    private String titulo;
    private Integer temporadas;
    private Integer numeroEpisodeo;
    private Double duracao;
    private LocalDate lancamento;
    private Double avaliacao;

    public Titulo(String type, String titulo, 
                        Integer temporadas,
                        Integer numeroEpisodeo,
                        Double duracao,
                        LocalDate lancamento,
                        Double avaliacao) {
        if (this.type == "movie") {
            // Tratar dados de filme
        } else {
            // Tratar dados de série
        }
        this.titulo = titulo;
        this.temporadas = temporadas;
        this.numeroEpisodeo = numeroEpisodeo;
        this.duracao = duracao;
        this.lancamento = lancamento;
        this.avaliacao = avaliacao;

        this.avaliacao = avaliacao;
    }

    public String getType() {
        return type;
    }
    public String getTitulo() {
        return titulo;
    }
    public Integer getTemporadas() {
        return temporadas;
    }
    public Integer getNumeroEpisodeo() {
        return numeroEpisodeo;
    }
    public Double getDuracao() {
        return duracao;
    }
    public LocalDate getLancamento() {
        return lancamento;
    }
    public Double getAvaliacao() {
        return avaliacao;
    }

}
