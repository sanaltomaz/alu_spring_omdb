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

    int opcao = -1;
    
    public Menus(
        TituloService tituloService,
        SerieAnaliseService serieAnalise
    ) {
        this.tituloService = tituloService;
        this.serieAnalise = serieAnalise;
    }
    
    public void iniciarMenu() {
        while (opcao != 0) {
            menuInicial();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Nome do título: ");
                    String nomeTitulo = scanner.nextLine();
                    
                    try {
                        Titulo serie = tituloService.buscarPorNome(nomeTitulo);
                        if (serie != null) {
                            OmdbSerieDto s = tituloService.buscarSerie(nomeTitulo);
                            opcoesSerie(s);
                        } else {
                            System.out.println("Título não encontrado.");
                        }
                        break;
                    } catch (Exception e) {
                        Titulo filme = tituloService.buscarPorNome(nomeTitulo);
                        System.out.println(filme);
                    }
                    
                case 2:
                    System.out.println("Saindo da aplicação. Até mais!");
                    opcao = 0;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    public void menuInicial() {
        System.out.println("Bem-vindo ao sistema OMDB!");
        System.out.println("1. Buscar Título");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public void opcoesSerie(OmdbSerieDto serie) {
        System.out.println("\nFunções disponíveis para séries:");
        System.out.println("1. Listar episódios");
        System.out.println("2. Exibir melhores episódios");
        System.out.println("3. Exibir piores episódios");
        System.out.println("4. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                System.out.println(serieAnalise.listarTodosEpisodios(serie.titulo()));
                break;
            case 2:
                System.out.println(serieAnalise.melhoresEpisodios(serie, 5));
                break;
            case 3:
                System.out.println(serieAnalise.pioresEpisodios(serie, 5));
                break;
            case 4:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}
