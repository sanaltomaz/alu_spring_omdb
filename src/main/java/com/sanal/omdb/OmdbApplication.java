package com.sanal.omdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.omdb.OmdbClient;
import com.sanal.omdb.dto.omdb.DadosFilme;
import com.sanal.omdb.services.TituloFactory;

// import com.sanal.omdb.principal.Principal;

@SpringBootApplication
public class OmdbApplication {
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(OmdbApplication.class, args);
		OmdbClient client = context.getBean(OmdbClient.class);

		Titulo t = new TituloFactory().fromFilme((DadosFilme) client.buscarTitulo("Inception"), null);
		System.out.println(t);

		// Object resultado = client.buscarTitulo("Inception");
		// Titulo t = new TituloFactory().fromFilme(client.buscarDetalhesDoFilme("Inception"), null);
		// System.out.println(t);
		// Principal principal = new Principal();

		// principal.iniciarAplicacao();
	}
}
