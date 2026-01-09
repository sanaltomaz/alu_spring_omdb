package com.sanal.omdb.omdb;

import org.springframework.stereotype.Component;

@Component
public class OmdbClient {
    /*
    Concentra tudo que é específico da API do OMDB.

    Responsabilidades:
    1. URL base
    2. Chave de API
    3. Formatos de requisição
    4. Chamada HTTP
    5. Tratamento básico de erros da API
    6. Conversão de JSON para DTOs externos

    Objetivo:
    - Isolar detalhes técnicos da OMDB
    - Limpar a lógica de negócio do restante da aplicação
    - Facilitar manutenção e futuras mudanças na API

    Não deve conter:
    7. Lógica de negócio da aplicação
    8. Decisão de fluxo
    9. Regras para decidir se o título é filme ou série no contexto do domínio
    10. Impressão de dados
    11. Controle de menus ou interação com usuário
    12. Validação de entrada do usuário

    Trade-offs assumidos:
    - Pipeline frágil substituído por um único ponto de integração
    - Múltiplas classes dependentes substituídas por fluxo centralizado e previsível
    - Nomes genéricos substituídos por responsabilidades explícitas
    */

}
