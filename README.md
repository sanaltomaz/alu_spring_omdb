# OMDB Application (Spring Boot)

Aplicação backend desenvolvida em Java com Spring Boot para consulta de títulos
(filmes e séries) utilizando a API pública do OMDB.

O projeto teve origem em exercícios de curso, mas evoluiu para uma arquitetura
própria, com separação clara de responsabilidades, integração externa centralizada
e preparação para expansão com persistência e interface web.

Atualmente, o projeto já não está mais diretamente acoplado ao material do curso
que o originou.

---

## Arquitetura

A aplicação segue um modelo em camadas, com foco em clareza, isolamento de
responsabilidades e evolução gradual.

Camadas utilizadas no momento:

- Interface (temporária)
  Camada de entrada baseada em menu de console (CLI).
  Existe apenas para interação local e validação de fluxos.
  Será substituída futuramente por controllers REST.

- Services de Aplicação
  Responsáveis por orquestrar os casos de uso.
  Coordenam chamadas ao client externo e a conversão de dados para o domínio.
  Exemplos:
  - TituloService
  - SerieAnaliseService

- Services de Domínio / Análise
  Contêm lógica de processamento e análise de dados já carregados.
  Não realizam chamadas HTTP, não interagem com o usuário e não dependem de UI.

- Integração Externa
  Comunicação com a API do OMDB centralizada em um client dedicado.
  Essa camada isola detalhes técnicos da API externa do restante da aplicação.

- Domínio
  Modelos internos que representam conceitos do sistema.
  Mantidos independentes de DTOs externos.

- DTOs Externos
  Estruturas utilizadas exclusivamente para mapear respostas da API do OMDB.
  Não fazem parte do domínio da aplicação.

---

## Configuração do Ambiente

A aplicação depende de variáveis de ambiente para acessar serviços externos.
É responsabilidade de quem executa o projeto configurá-las corretamente
em sua máquina local.

Crie um arquivo .env na raiz do projeto com as seguintes variáveis:

API_KEY=sua_chave_da_omdb
ENDERECO=https://www.omdbapi.com/?t=

Descrição das variáveis:

- API_KEY
  Chave de acesso fornecida pela API do OMDB.

- ENDERECO
  Endpoint base utilizado para consultas por título.
  A composição final da URL é responsabilidade do client de integração.

---

## Execução

A aplicação pode ser executada a partir da classe principal:

com.sanal.omdb.OmdbApplication

No estado atual, a aplicação inicia um menu em modo CLI que permite:
- buscar filmes e séries
- listar episódios de séries
- exibir melhores e piores episódios com base em avaliação

---

## Estado Atual do Projeto

Funcionalidades implementadas:
- Integração com a API do OMDB
- Consulta de filmes e séries
- Análise de episódios de séries
- Arquitetura preparada para expansão

Funcionalidades planejadas:
- Tratamento explícito de erros de domínio
- Persistência de dados com JPA
- Exposição de endpoints REST
- Integração com serviços externos adicionais

---

## Observações

- O menu em modo texto é temporário e existe apenas como ferramenta de apoio
  durante o desenvolvimento.
- A lógica de negócio não depende da interface atual.
- O projeto está estruturado para permitir evolução sem necessidade de grandes
  refatorações.
