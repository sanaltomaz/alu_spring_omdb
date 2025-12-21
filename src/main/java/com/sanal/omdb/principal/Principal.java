package com.sanal.omdb.principal;

import java.util.Scanner;
import com.sanal.omdb.services.IdentificarTipo;
import com.sanal.omdb.services.RetornoDados;
import com.sanal.omdb.models.DadosFilme;
import com.sanal.omdb.models.DadosSerie;
import com.sanal.omdb.services.ConverteDados;

public class Principal {
    
    private Scanner scanner = new Scanner(System.in);
    // private Funcoes Funcoes = new Funcoes();
    private Menus menus = new Menus();
    
    private IdentificarTipo identificador = new IdentificarTipo();
    private RetornoDados converte = new RetornoDados();

    public void iniciarAplicacao() {
        menus.iniciarMenus();
        int opcao = scanner.nextInt();
        scanner.nextLine(); 

        try {
            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome de um Titulo: ");
                    String nomeTitulo = scanner.nextLine();

                    var json = converte.retornarDadosTitulo(nomeTitulo);

                    Class<?> tipoClass = identificador.identificarTipo(json);
                    Object dados = new ConverteDados().obterDados(json, tipoClass);

                    if (dados instanceof DadosSerie) {
                        menus.opcoesSerie();
                        int escolha = scanner.nextInt();
                        scanner.nextLine();
                        menus.funcoesMenuSerie((DadosSerie) dados, escolha);
                    } else if (dados instanceof DadosFilme) {
                        System.out.println((DadosFilme) dados);
                    } else {
                        System.out.println("Tipo de título desconhecido.");
                    }

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
