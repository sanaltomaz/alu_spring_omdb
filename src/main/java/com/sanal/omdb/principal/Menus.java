package com.sanal.omdb.principal;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.sanal.omdb.dto.omdb.OmdbSerieDto;
import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.services.SerieAnaliseService;
import com.sanal.omdb.services.TituloService;

@Component
public class Menus {

    private final TituloService tituloService;
    private final SerieAnaliseService serieAnalise;
    private final Scanner scanner = new Scanner(System.in);

    public Menus(
        TituloService tituloService,
        SerieAnaliseService serieAnalise
    ) {
        this.tituloService = tituloService;
        this.serieAnalise = serieAnalise;
    }

    public void iniciarMenu() {
        int opcao = -1;

        while (opcao != 0) {
            exibirMenuInicial();
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> buscarTitulo();
                case 2 -> {
                    encerrar();
                    opcao = 0;
                }
    default -> System.out.println("Opção inválida.");
}

        }
    }

    private void exibirMenuInicial() {
        System.out.println("\n=== OMDB ===");
        System.out.println("1. Buscar título");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    private void buscarTitulo() {
        System.out.print("\nNome do título: ");
        String nome = scanner.nextLine();

        Titulo titulo = tituloService.buscarPorNome(nome);

        if (titulo == null) {
            System.out.println("Título não encontrado.");
            return;
        }

        // Se chegou aqui, existe título
        OmdbSerieDto serie = tituloService.buscarSerie(nome);

        if (serie != null && "series".equalsIgnoreCase(serie.type())) {
            menuSerie(serie);
        } else {
            System.out.println(titulo);
        }

    }

    private void menuSerie(OmdbSerieDto serie) {
        int opcao = -1;

        while (opcao != 0) {
            exibirMenuSerie(serie);
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> listarEpisodios(serie);
                case 2 -> listarMelhoresEpisodios(serie);
                case 3 -> listarPioresEpisodios(serie);
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void exibirMenuSerie(OmdbSerieDto serie) {
        System.out.println("\n=== Série: " + serie.titulo() + " ===");
        System.out.println("1. Listar episódios");
        System.out.println("2. Melhores episódios");
        System.out.println("3. Piores episódios");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private void listarEpisodios(OmdbSerieDto serie) {
        System.out.println(
            serieAnalise.listarTodosEpisodios(serie.titulo())
        );
    }

    private void listarMelhoresEpisodios(OmdbSerieDto serie) {
        serieAnalise
            .melhoresEpisodios(serie, 5)
            .forEach(System.out::println);
    }

    private void listarPioresEpisodios(OmdbSerieDto serie) {
        serieAnalise
            .pioresEpisodios(serie, 5)
            .forEach(System.out::println);
    }

    private void encerrar() {
        System.out.println("Saindo da aplicação. Até mais!");
    }
}
