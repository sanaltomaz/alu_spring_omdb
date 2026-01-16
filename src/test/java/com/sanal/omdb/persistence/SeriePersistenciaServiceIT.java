package com.sanal.omdb.persistence;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Teste de integração para {@code SeriePersistenciaService}.
 *
 * Objetivo:
 * - Validar a persistência real de séries em banco PostgreSQL
 * - Verificar a conversão correta do domínio para entidade JPA
 * - Garantir que apenas títulos do tipo SERIE sejam persistidos
 *
 * Tipo de teste:
 * - Teste de integração (não é teste unitário)
 * - Utiliza banco PostgreSQL real configurado no profile de teste
 *
 * Escopo do que é testado:
 * - Persistência bem-sucedida de uma série válida
 * - Geração correta de identificador
 * - Escrita efetiva no banco de dados
 * - Validação de uso correto do service (tipo do título)
 *
 * O que NÃO é testado aqui:
 * - Persistência de episódios
 * - Integração com a API do OMDB
 * - Regras de análise ou estatísticas
 * - Fluxos de aplicação completos
 *
 * Observações:
 * - Este teste valida o aggregate root Série em isolamento
 * - Episódios são tratados em testes específicos
 * - Falhas aqui indicam inconsistência direta na persistência de séries
 */
@SpringBootTest
@ActiveProfiles("test")
public class SeriePersistenciaServiceIT {

    // Testes serão adicionados incrementalmente

}
