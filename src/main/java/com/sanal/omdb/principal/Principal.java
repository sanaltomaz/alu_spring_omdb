package com.sanal.omdb.principal;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.Scanner;
import com.sanal.omdb.services.ConsumoApi;

public class Principal {
    Dotenv dotenv = Dotenv.load();
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();

    String endereco = dotenv.get("ENDERECO");
    String apiKey = dotenv.get("API_KEY");

    public void Menu() {
        System.out.println("Digite o nome de um filme: ");
        String nomeDoFilme = scanner.nextLine();
        var json = consumo.obterDados(endereco + nomeDoFilme + apiKey);
        System.out.println(json);
    }
    
}
