package com.sanal.omdb.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sanal.omdb.models.TipoTitulo;
import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.persistence.entity.FilmeEntity;
import com.sanal.omdb.persistence.repository.FilmeRepository;
import com.sanal.omdb.persistence.service.FilmePersistenciaService;

/**
 * Teste de integração para {@code FilmePersistenciaService}.
 *
 * Objetivo:
 * - Validar a persistência real de filmes em banco PostgreSQL
 * - Verificar integração entre Service, Mapper, Repository e JPA
 * - Garantir que transações simples funcionam conforme esperado
 *
 * Tipo de teste:
 * - Teste de integração (não é teste unitário)
 * - Utiliza banco PostgreSQL real configurado no profile de teste
 *
 * Escopo do que é testado:
 * - Persistência bem-sucedida de um filme válido
 * - Geração correta de identificador
 * - Escrita efetiva no banco de dados
 *
 * O que NÃO é testado aqui:
 * - Integração com a API do OMDB
 * - Lógica de negócio ou análise
 * - Casos de erro de domínio
 * - Comportamento de outros services
 *
 * Observações:
 * - Este teste depende de um banco PostgreSQL local configurado
 * - O schema é criado e destruído automaticamente para cada execução
 * - Falhas neste teste indicam problemas reais de persistência
 */
@SpringBootTest
@ActiveProfiles("test")
public class FilmePersistenciaServiceIT {

    @Autowired
    private FilmePersistenciaService filmePersistenciaService;

    @Autowired
    private FilmeRepository filmeRepository;

    @Test
    void devePersistirFilmeComSucesso() {
        
        // given - um domínio válido

        Titulo titulo = new Titulo(
            TipoTitulo.FILME,
            "The Matrix",
            null,
            null,
            null,
            8.7,
            null,
            null
        );

        // when - persistimos usando o service real
        FilmeEntity entity = filmePersistenciaService.salvarFilme(titulo);

        // then - validamos efeitos reais no banco
        assertNotNull(entity.getId(), "O filme deveria ter um ID gerado");
        assertEquals(1, filmeRepository.count(), "Deveria existir exatamente um filme persistido");
    }

    @Test
    void deveFalharAoPersistirTituloQueNaoEhFilme() {
        // given - título inválido para este service
        Titulo titulo = new Titulo(
            TipoTitulo.SERIE,
            "Breaking Bad",
            null,
            null,
            null,
            9.5,
            null,
            null
        );

        // when / then - deve falhar
        assertThrows(
            IllegalStateException.class,
            () -> filmePersistenciaService.salvarFilme(titulo),
            "Persistir um título que não é FILME deve falhar"
        );

        // e nada deve ter sido persistido
        assertEquals(
            0,
            filmeRepository.count(),
            "Nenhum filme deve ser persistido após falha"
        );
    }

    @Test
    void deveFalharAoPersistirTituloNulo() {
        // when / then
        assertThrows(
            NullPointerException.class,
            () -> filmePersistenciaService.salvarFilme(null),
            "Persistir um título nulo deve falhar"
        );
    
        // e nada deve ter sido persistido
        assertEquals(
            0,
            filmeRepository.count(),
            "Nenhum filme deve ser persistido ao receber título nulo"
        );
    }

}
