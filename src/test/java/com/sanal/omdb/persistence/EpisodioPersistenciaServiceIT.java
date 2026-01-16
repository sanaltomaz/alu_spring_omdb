package com.sanal.omdb.persistence;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
 * - Rollback completo da temporada em caso de erro
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
public class EpisodioPersistenciaServiceIT {

    // Testes serão adicionados incrementalmente

}
