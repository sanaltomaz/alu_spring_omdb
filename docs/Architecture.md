# Arquitetura do Projeto OMDB

## Visão Geral

Este documento registra as **principais decisões arquiteturais** do projeto OMDB.

O objetivo não é descrever detalhes de implementação, mas documentar **intenções, limites e trade-offs** assumidos ao longo da evolução do sistema, servindo como referência para manutenção e expansão futura.

---

## Princípios Gerais

As decisões arquiteturais do projeto seguem os princípios abaixo:

* Separação clara de responsabilidades entre camadas
* Isolamento de integrações externas
* Domínio independente de frameworks e APIs externas
* Evolução incremental, evitando antecipar complexidade
* Clareza e previsibilidade acima de abstrações sofisticadas

O projeto prioriza **coerência arquitetural** em vez de soluções completas desde o início.

---

## Integração com a API do OMDB

A comunicação com a API do OMDB é centralizada em um **client dedicado**.

Decisões:

* DTOs da OMDB existem apenas na camada de integração
* Dados instáveis ou inconsistentes da API são tratados na borda do sistema
* Nenhuma outra camada conhece detalhes da API externa

Motivações:

* Reduzir acoplamento
* Facilitar substituição ou extensão futura da integração
* Evitar contaminação do domínio com regras externas

Observação adicional:

* Tratamentos defensivos simples de dados externos (ex.: valores ausentes ou inválidos)
  podem ocorrer em mappers de persistência, desde que não envolvam regras de negócio
  ou decisões de fluxo.

---

## Domínio

O domínio representa os conceitos centrais do sistema, como títulos, filmes, séries e episódios.

Decisões importantes:

* O domínio **não depende** de DTOs externos
* O domínio **não depende** de JPA ou detalhes de persistência
* Conversões externas → domínio são realizadas por factories dedicadas

O domínio é tratado como a parte mais estável do sistema.

---

## Persistência

### Aggregate Root

* **Série** é tratada como *aggregate root*
* **Episódios** dependem de uma série existente
* Episódios **não existem de forma independente** no sistema

Motivações:

* Episódios não fazem sentido sem série
* Garantia de ordem e consistência na persistência
* Evita entidades órfãs

---

### Persistência de Episódios

A persistência de episódios segue regras explícitas:

* A série deve estar persistida antes de qualquer episódio
* Episódios são persistidos **por temporada**
* Cada temporada é tratada como **unidade transacional**
* Não existe transação global envolvendo todas as temporadas da série

Consequências assumidas:

* Falha em uma temporada não afeta temporadas já persistidas
* O sistema aceita estados parciais por série
* Idempotência não é garantida neste estágio

Essas decisões evitam transações longas e simplificam o controle de falhas.

---

### Cascade e Relacionamentos

Decisões atuais:

* Não é utilizado cascade JPA
* Relacionamentos são unidirecionais
* A ordem de persistência é controlada manualmente pelos services

Motivações:

* Evitar complexidade prematura
* Tornar a persistência explícita e previsível
* Facilitar entendimento do fluxo

Essas decisões podem ser revisitadas conforme o projeto evoluir.

---

## Services

### Services de Aplicação

Responsáveis por:

* Orquestrar casos de uso
* Coordenar chamadas à integração externa
* Decidir quando e como dados são persistidos

Não fazem:

* Lógica de persistência detalhada
* Parsing de dados externos
* Regras de análise complexas

---

### Services de Persistência

Responsáveis por:

* Persistir entidades após validações de uso e integridade estrutural
* Garantir a ordem correta de persistência
* Controlar atomicidade e escopo transacional

Não fazem:

* Busca de dados externos
* Decisão de fluxo de aplicação
* Regras de negócio ou análise

---

## O que o Projeto NÃO Resolve (por enquanto)

As seguintes preocupações são conhecidas, mas **deliberadamente não resolvidas** neste estágio:

* Idempotência na persistência de episódios
* Atualização incremental de episódios
* Controle de concorrência
* Otimizações de performance em lote
* Tratamento avançado de erros de domínio

Esses pontos serão abordados conforme surgirem necessidades reais.

---

## Considerações Finais

Este documento deve evoluir junto com o projeto.

Sempre que uma decisão arquitetural relevante for revisitada ou alterada, este arquivo deve ser atualizado para refletir o novo entendimento do sistema.
