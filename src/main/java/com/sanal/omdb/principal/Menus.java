package com.sanal.omdb.principal;


import com.sanal.omdb.models.*;

public class Menus {
    private Funcoes funcoes = new Funcoes();

    public void iniciarMenus() {
        System.out.println("Bem-vindo ao sistema OMDB!");
        System.out.println("1. Buscar Título");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public void opcoesSerie() {
        System.out.println("Funções disponíveis para séries:");
        System.out.println("1. Listar episódios");
        System.out.println("2. Exibir melhores episódios");
        System.out.println("3. Exibir piores episódios");
        System.out.println("4. Exibir estatísticas da série");
        System.out.println("5. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
    }

    public void funcoesMenuSerie(DadosSerie serie, int opcao) {
        switch (opcao) {
            case 1:
                funcoes.listarEpisodios(serie).forEach(System.out::println);
                break;
            case 2:
                funcoes.listarMelhoresEpisodios(serie);
                break;
            case 3:
                funcoes.listarPioresEpisodios(serie);
                break;
            case 4:
                funcoes.exibirEstatisticasSerie(serie);
                break;
            case 5:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}
