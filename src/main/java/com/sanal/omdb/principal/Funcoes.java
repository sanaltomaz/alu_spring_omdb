package com.sanal.omdb.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import com.sanal.omdb.models.*;

import com.sanal.omdb.services.ConverteDados;
import com.sanal.omdb.services.RetornoDados;

public class Funcoes {
    private ConverteDados converte = new ConverteDados();
    private RetornoDados retorno = new RetornoDados();

    public List<DadosTemporada> listarEpisodios(DadosSerie serie) {
        System.out.println("Listando episódios da temporada da série: " + serie.titulo());

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= serie.temporadas(); i++) {
            String json = retorno.retornarDadosEpisodeos(
                serie.titulo(), i
            );

            DadosTemporada dadosTemporada =
                converte.obterDados(json, DadosTemporada.class);

            temporadas.add(dadosTemporada);
        }

        return temporadas;
    }

    public void listarMelhoresEpisodios(DadosSerie serie) {
        System.out.println("Listando melhores episódios...");

        var temporadas = listarEpisodios(serie);

        List<DadosEpisodio> dadosEpisodio = temporadas.stream()
            .flatMap(t -> t.episodios().stream())
            .collect(Collectors.toList());

        dadosEpisodio.stream()
            .filter(e -> !e.avaliacao().equals("N/A"))
            .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
            .limit(5)
            .forEach(System.out::println);
    }

    public void listarPioresEpisodios(DadosSerie serie) {
        System.out.println("Listando piores episódios...");

        var temporadas = listarEpisodios(serie);

        List<DadosEpisodio> dadosEpisodio = temporadas.stream()
            .flatMap(t -> t.episodios().stream())
            .collect(Collectors.toList());

        dadosEpisodio.stream()
            .filter(e -> !e.avaliacao().equals("N/A"))
            .sorted(Comparator.comparing(DadosEpisodio::avaliacao))
            .limit(5)
            .forEach(System.out::println);
    }

    public void exibirEstatisticasSerie(DadosSerie serie) {
        System.out.println("Exibindo estatísticas da série: " + serie.titulo());

        var temporadas = listarEpisodios(serie);

        List<Titulo> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Titulo(d)))
                .collect(Collectors.toList());
        
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Titulo::getAvaliacao));

        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());

    }
}
