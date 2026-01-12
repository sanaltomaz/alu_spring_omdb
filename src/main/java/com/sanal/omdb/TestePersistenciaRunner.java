package com.sanal.omdb;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.services.TituloService;
import com.sanal.omdb.persistence.service.FilmePersistenciaService;

@Configuration
public class TestePersistenciaRunner {

    @Bean
    ApplicationRunner testarPersistenciaFilme(
        TituloService tituloService,
        FilmePersistenciaService filmePersistenciaService
    ) {
        return args -> {
            Titulo titulo = tituloService.buscarPorNome("Inception");

            filmePersistenciaService.salvarFilme(titulo);

            System.out.println("Filme persistido com sucesso");
        };
    }
}
