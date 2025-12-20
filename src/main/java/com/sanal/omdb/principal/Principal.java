package com.sanal.omdb.principal;

import java.util.Scanner;
import com.sanal.omdb.services.IdentificarTipo;
import com.sanal.omdb.models.DadosSerie;
import com.sanal.omdb.services.ConverteDados;

public class Principal {
    
    private Scanner scanner = new Scanner(System.in);
    private Funcoes Funcoes = new Funcoes();
    private Menus menus = new Menus();
    
    private IdentificarTipo identificador = new IdentificarTipo();

    public void iniciarAplicacao() {
        menus.iniciarMenus();
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        try {
            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome de um Titulo: ");
                    String nomeDoFilme = scanner.nextLine();

                    var json = Funcoes.retornarDadosTitulo(nomeDoFilme);

                    Class<?> tipoClass = identificador.identificarTipo(json);
                    Object dados = new ConverteDados().obterDados(json, tipoClass);
                    System.out.println(dados);

                    Funcoes.listarEpisodios((DadosSerie) dados);
                    // buscarTitulo();
                    break;
                case 2:
                    System.out.println("Saindo da aplicação. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }
    }
}
