package com.sanal.omdb.principal;

import java.util.Scanner;

import com.sanal.omdb.dto.omdb.DadosFilme;
import com.sanal.omdb.dto.omdb.DadosSerie;
import com.sanal.omdb.models.*;
import com.sanal.omdb.services.ConverteDados;
import com.sanal.omdb.services.IdentificarTipo;
import com.sanal.omdb.services.RetornoDados;
import com.sanal.omdb.services.TituloFactory;

public class Menus {
    private Funcoes funcoes = new Funcoes();
    private Scanner scanner = new Scanner(System.in);
    private IdentificarTipo identificador = new IdentificarTipo();
    private RetornoDados converte = new RetornoDados();

    int opcao = -1;
    
    public void iniciarMenu() {
        while (opcao != 0) {
            menuInicial();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Nome do título: ");
                    String nomeTitulo = scanner.nextLine();

                    var json = converte.retornarDadosTitulo(nomeTitulo);

                    Class<?> tipoClass = identificador.identificarTipo(json);
                    Object dados = new ConverteDados().obterDados(json, tipoClass);

                    if (dados instanceof DadosSerie) {
                        opcoesSerie((DadosSerie) dados);
                    } else if (dados instanceof DadosFilme) {
                        Titulo t = new TituloFactory().fromFilme((DadosFilme) dados, null);
                        System.out.println(t);
                    } else {
                        System.out.println("Tipo de título desconhecido.");
                    }
                    break;

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

    public void opcoesSerie(DadosSerie serie) {
        System.out.println("Funções disponíveis para séries:");
        System.out.println("1. Apresentar série");
        System.out.println("2. Listar episódios");
        System.out.println("3. Exibir melhores episódios");
        System.out.println("4. Exibir piores episódios");
        System.out.println("5. Exibir estatísticas da série");
        System.out.println("6. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                Titulo tituloSerie = new TituloFactory().fromSerie(serie, null);
                System.out.println(tituloSerie);
                break;
            case 2:
                funcoes.listarEpisodios(serie).forEach(System.out::println);
                break;
            case 3:
                funcoes.listarMelhoresEpisodios(serie);
                break;
            case 4:
                funcoes.listarPioresEpisodios(serie);
                break;
            case 5:
                funcoes.exibirEstatisticasSerie(serie);
                break;
            case 6:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}
