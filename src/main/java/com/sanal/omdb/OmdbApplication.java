package com.sanal.omdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sanal.omdb.principal.Principal;

@SpringBootApplication
public class OmdbApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OmdbApplication.class, args);
		Principal principal = new Principal();

		principal.iniciarAplicacao();
	}
}
