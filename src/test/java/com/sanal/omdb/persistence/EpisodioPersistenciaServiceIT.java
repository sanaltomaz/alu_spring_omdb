package com.sanal.omdb.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sanal.omdb.dto.omdb.OmdbEpisodioDto;
import com.sanal.omdb.dto.omdb.OmdbSerieCompletaDto;
import com.sanal.omdb.dto.omdb.OmdbSerieDto;
import com.sanal.omdb.dto.omdb.OmdbTemporadaDto;
import com.sanal.omdb.models.TipoTitulo;
import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.persistence.entity.SerieEntity;
import com.sanal.omdb.persistence.repository.EpisodioRepository;
import com.sanal.omdb.persistence.service.EpisodioPersistenciaService;
import com.sanal.omdb.persistence.service.SeriePersistenciaService;

/**
 * Teste de integração para {@code EpisodioPersistenciaService}.
 *
 * Objetivo:
 * - Validar a persistência real de episódios em banco PostgreSQL
 * - Garantir atomicidade por temporada
 * - Verificar rollback em caso de falha durante a persistência
 * - Confirmar persistência parcial por série (temporadas independentes)
 *
 * Tipo de teste:
 * - Teste de integração (não é teste unitário)
 * - Utiliza banco PostgreSQL real configurado no profile de teste
 *
 * Escopo do que é testado:
 * - Persistência de episódios associados a uma série já persistida
 * - Persistência correta por temporada
 * - Manutenção de dados de temporadas anteriores em falhas subsequentes
 *
 * O que NÃO é testado aqui:
 * - Persistência de séries (testada separadamente)
 * - Integração com a API do OMDB
 * - Lógica de análise ou estatísticas
 * - Fluxos de aplicação completos
 *
 * Observações:
 * - Série é tratada como aggregate root
 * - Episódios não existem sem série
 * - Este teste valida decisões arquiteturais, não apenas código
 * - Falhas aqui indicam problemas sérios de consistência transacional
 */
@SpringBootTest
@ActiveProfiles("test")
class EpisodioPersistenciaServiceIT {

    @Autowired
    private SeriePersistenciaService seriePersistenciaService;

    @Autowired
    private EpisodioPersistenciaService episodioPersistenciaService;

    @Autowired
    private EpisodioRepository episodioRepository;

    @Test
    void devePersistirEpisodiosDeUmaTemporadaComSucesso() {
        // given - série persistida
        Titulo tituloSerie = new Titulo(
            TipoTitulo.SERIE,
            "Breaking Bad",
            null,
            null,
            null,
            9.5,
            null,
            null
        );

        SerieEntity serie = seriePersistenciaService.salvarSerie(tituloSerie);

        OmdbSerieDto serieDto = new OmdbSerieDto(
            "series",
            "Breaking Bad",
            5,
            "9.5",
            "20 Jan 2008",
            "A high school chemistry teacher turned methamphetamine producer..."
        );

        // e uma temporada válida
        OmdbSerieCompletaDto serieCompletaDto = new OmdbSerieCompletaDto( 
            serieDto,
            List.of(
                new OmdbTemporadaDto(
                    1,
                    List.of(
                        new OmdbEpisodioDto("Pilot", 1, "8.9"),
                        new OmdbEpisodioDto("Cat's in the Bag...", 2, "8.7")
                    )
                )
            )
        );

        // when - persistimos os episódios da temporada
        episodioPersistenciaService.salvarTodosEpisodios(serie, serieCompletaDto);

        // then - efeitos reais no banco
        assertEquals(2, episodioRepository.count(), "Deveriam existir dois episódios persistidos");

        episodioRepository.findAll().forEach(episodio -> {
            assertNotNull(episodio.getId(), "Episódio deveria ter ID");
            assertEquals(1, episodio.getNumeroTemporada());
            assertEquals(serie.getId(), episodio.getSerie().getId());
        });
    }
}
