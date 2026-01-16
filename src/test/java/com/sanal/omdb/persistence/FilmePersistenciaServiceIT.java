package com.sanal.omdb.persistence;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

    // Testes serão adicionados incrementalmente

}
