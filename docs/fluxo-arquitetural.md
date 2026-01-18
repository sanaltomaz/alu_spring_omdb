# Fluxo Arquitetural do Projeto OMDB

Este documento descreve os **principais fluxos arquiteturais** do projeto CineCatalog.

O objetivo é explicar **como as responsabilidades fluem entre camadas**, sem entrar em detalhes de implementação, código ou frameworks específicos.

Os fluxos aqui descritos refletem o estado atual do sistema.

---

## Princípios do Fluxo

Os fluxos arquiteturais seguem os princípios abaixo:

* Cada camada possui responsabilidade bem definida
* Dependências sempre apontam para dentro (domínio)
* Integrações externas são tratadas na borda do sistema
* Persistência é explícita e controlada
* Nenhuma camada conhece mais do que precisa

---

## Fluxo 1: Buscar Série e Persistir seus Episódios

Este é o fluxo mais completo do sistema atualmente.

### 1. Interface (CLI)

* Recebe o nome da série
* Não contém lógica de negócio
* Não conhece OMDB, JPA ou detalhes internos
* Apenas dispara o caso de uso

---

### 2. Service de Aplicação

* Orquestra o caso de uso
* Decide que dados devem ser buscados
* Solicita dados ao client OMDB
* Coordena a persistência

Responsabilidade principal:
> Controlar o fluxo, não processar dados.

---

### 3. Client de Integração OMDB

* Monta URLs e parâmetros
* Executa chamadas HTTP
* Recebe respostas da API externa
* Retorna DTOs externos

Observações:
* Não persiste dados
* Não conhece domínio
* Não decide fluxo

---

### 4. Conversão para Domínio

* DTOs externos são convertidos para objetos de domínio
* Inconsistências da API são tratadas
* Domínio permanece limpo e independente

Essa conversão ocorre antes de qualquer persistência.

---

### 5. Services de Persistência

Responsabilidades:

* Validar pré-condições de uso
  * Tipo do título
  * Ordem de persistência
  * Série já existente
* Controlar transações
* Persistir entidades

Decisão importante:
* Episódios são persistidos **por temporada**
* Cada temporada é uma unidade transacional independente

---

### 6. Repositories

* Executam operações de banco de dados
* Não conhecem domínio, OMDB ou fluxo
* Utilizam apenas contratos do Spring Data JPA

---

### 7. Banco de Dados

* Armazena o estado persistido
* Não contém lógica de negócio
* Reflete decisões tomadas nas camadas superiores

---

## Fluxo 2: Persistência Parcial de Série

Este fluxo descreve o comportamento do sistema em caso de falha.

### Cenário

* Temporada 1 persiste com sucesso
* Temporada 2 falha durante a persistência

### Comportamento do sistema

* A transação da temporada 2 é revertida
* A temporada 1 permanece persistida
* O sistema aceita estado parcial da série

Motivação:
* Evitar transações longas
* Simplificar controle de falhas
* Manter previsibilidade do sistema

---

## Fluxos Não Implementados (por enquanto)

Os seguintes fluxos são conhecidos, mas ainda não existem:

* Atualização incremental de episódios
* Reprocessamento de temporadas
* Sincronização entre execuções
* Exposição via API REST

Esses fluxos serão documentados quando implementados.

---

## Considerações Finais

Este documento deve evoluir junto com o sistema.

Sempre que um fluxo arquitetural mudar de forma relevante, este arquivo deve ser atualizado para refletir o novo comportamento do projeto.
