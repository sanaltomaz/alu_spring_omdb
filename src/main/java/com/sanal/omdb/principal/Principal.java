package com.sanal.omdb.principal;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.sanal.omdb.services.ConsumoApi;
import com.sanal.omdb.services.IdentificarClasse;
import com.sanal.omdb.services.ConverteDados;
import com.sanal.omdb.models.*;

public class Principal {
    Dotenv dotenv = Dotenv.load();
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private IdentificarClasse identificador = new IdentificarClasse();
    private ConverteDados converte = new ConverteDados();

    String endereco = dotenv.get("ENDERECO");
    String apiKey = dotenv.get("API_KEY");

    public void iniciarMenu() {
        System.out.println("Digite o nome de um Titulo: ");
        String nomeDoFilme = scanner.nextLine();

        var json = consumo.obterDados(
            endereco + nomeDoFilme.replace(" ", "+") + apiKey
        );

        Class<?> classe = identificador.identificarTipo(json);

        if (classe == DadosFilme.class) {
            DadosFilme dados = converte.obterDados(json, DadosFilme.class);
            Titulo titulo = new Titulo(dados);
            System.out.println(titulo);
        } else if (classe == DadosSerie.class) {
            DadosSerie dados = converte.obterDados(json, DadosSerie.class);
            Titulo titulo = new Titulo(dados);
            // System.out.println(titulo);

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= titulo.getTemporadas(); i++) {
                json = consumo.obterDados(
                    endereco + nomeDoFilme.replace(" ", "+") + "&season=" + i + apiKey
                );
                DadosTemporada dadosTemporada = converte.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);


        } else if (classe == DadosEpisodio.class) {
            DadosEpisodio dados = converte.obterDados(json, DadosEpisodio.class);
            Titulo titulo = new Titulo(dados);
            System.out.println(titulo);
        }

    }
}
