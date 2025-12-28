package com.sanal.omdb.models;

import java.time.DateTimeException;
import java.time.LocalDate;

import com.sanal.omdb.services.ConsumoGPT;

public class Titulo {
    private String type;
    private String titulo;
    private Integer temporadas;
    private Integer numeroEpisodeo;
    private Double duracao;
    private Double avaliacao;
    private LocalDate dataLancamento;
    private String sinapse;

    public Titulo (DadosFilme dados) {
        this.type = dados.type();
        this.titulo = dados.titulo();
        this.sinapse = ConsumoGPT.obterTraducao(dados.sinapse().trim());
        this.duracao = Double.valueOf(dados.duracao().replace(" min", ""));

        try {
            this.avaliacao = Double.valueOf(dados.avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        }

        try {
            this.dataLancamento = LocalDate.parse(dados.dataLancamento());
        } catch (DateTimeException ex) {
            this.dataLancamento = null;
        }
    }

    public Titulo (DadosSerie dados) {
        this.type = dados.type();
        this.titulo = dados.titulo();
        this.sinapse = ConsumoGPT.obterTraducao(dados.sinapse().trim());
        this.temporadas = Integer.valueOf(dados.temporadas());

        try {
            this.avaliacao = Double.valueOf(dados.avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        }

        try {
            this.dataLancamento = LocalDate.parse(dados.dataLancamento());
        } catch (DateTimeException ex) {
            this.dataLancamento = null;
        }
    }

    public Titulo (DadosEpisodio dados) {
        this.titulo = dados.titulo();
        this.numeroEpisodeo = dados.episodio();

        try {
            this.avaliacao = Double.valueOf(dados.avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        }
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
    public Double getAvaliacao() {
        return avaliacao;
    }
    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    @Override
    public String toString() {
    
        if ("movie".equalsIgnoreCase(type)) {
            return """
                Filme: %s
                Avaliação: %.1f
                Duração: %.0f min
                Lançamento: %s
                Sinopse: %s
                """.formatted(titulo, avaliacao, duracao, dataLancamento, sinapse);
        }
    
        if ("series".equalsIgnoreCase(type)) {
            return """
                Série: %s
                Temporadas: %d
                Avaliação: %.1f
                Lançamento: %s
                Sinopse: %s
                """.formatted(titulo, temporadas, avaliacao, dataLancamento, sinapse);
        }
    
        if (numeroEpisodeo != null) {
            return """
                Episódio: %s
                Número: %d
                Avaliação: %.1f
                """.formatted(titulo, numeroEpisodeo, avaliacao);
        }
    
        return titulo;
    }
}