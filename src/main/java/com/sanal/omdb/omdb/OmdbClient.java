package com.sanal.omdb.omdb;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanal.omdb.dto.omdb.DadosEpisodio;
import com.sanal.omdb.dto.omdb.DadosFilme;
import com.sanal.omdb.dto.omdb.DadosSerie;

import io.github.cdimascio.dotenv.Dotenv;

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
     6. Conversão de respostas JSON da OMDB para DTOs externos

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

    private Dotenv dotenv = Dotenv.load();
    private String endereco = dotenv.get("ENDERECO");
    private String apiKey = dotenv.get("API_KEY");

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Executa a chamada HTTP para a API da OMDB.
     *
     * Responsabilidade:
     * - Realizar a requisição HTTP
     * - Retornar o corpo da resposta como JSON bruto
     *
     * Observações:
     * - Não interpreta o conteúdo da resposta
     * - Não valida dados de domínio
     * - Centraliza erros de comunicação externa
     */
    private String consumirApiOmdb(String endereco) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = null;

            try {
                response = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consumir a API OMDB", e);
        }
    }

    /**
     * Monta a URL de consulta da OMDB e busca os dados do título pelo nome.
     *
     * Responsabilidade:
     * - Preparar a requisição específica da OMDB
     * - Delegar a chamada HTTP para o método de consumo
     *
     * Retorno:
     * - JSON bruto retornado pela API da OMDB
     *
     * Observações:
     * - Não identifica tipo
     * - Não converte para objetos
     */
    private String buscarTituloPorNome(String nome) {
        String url = endereco + nome.replace(" ", "+") + apiKey;
        return consumirApiOmdb(url);
    }

    /**
     * Identifica o tipo técnico do título com base no formato da resposta da OMDB.
     *
     * Responsabilidade:
     * - Inspecionar o JSON bruto retornado pela API
     * - Determinar qual DTO externo representa corretamente o conteúdo
     *
     * Observações:
     * - A lógica depende do formato da OMDB
     * - Deve permanecer encapsulada neste client
     * - Não representa regra de negócio do domínio
     */
    private Class<?> identificarTipoDoTitulo(String json) {
        try {
            if (json.contains("\"Type\":\"movie\"")) {
                return DadosFilme.class;
            } else if (json.contains("\"Type\":\"series\"")) {
                return DadosSerie.class;
            } else if (json.contains("\"Episode\":")) {
                return DadosEpisodio.class;
            } else {
                throw new IllegalArgumentException("Tipo desconhecido no JSON");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao identificar tipo do título", e);
        }
    }

    /**
     * Método principal do OmdbClient.
     *
     * Responsabilidade:
     * - Coordenar a busca do título na OMDB
     * - Identificar o tipo técnico do conteúdo retornado
     * - Converter o JSON para o DTO externo correspondente
     *
     * Retorno:
     * - DTO externo da OMDB (ex: DadosFilme ou DadosSerie)
     *
     * Observações:
     * - Encapsula completamente detalhes da API
     * - Não expõe JSON para o restante da aplicação
     * - Retorno genérico é temporário e será refinado em etapas futuras
     */
    public Object buscarTitulo(String nome) {
        String json = buscarTituloPorNome(nome);
        Class<?> tipo = identificarTipoDoTitulo(json);

        try {
            if (tipo.equals(DadosFilme.class)) {
                return mapper.readValue(json, DadosFilme.class);
            }

            if (tipo.equals(DadosSerie.class)) {
                return mapper.readValue(json, DadosSerie.class);
            }

            if (tipo.equals(DadosEpisodio.class)) {
                return mapper.readValue(json, DadosEpisodio.class);
            }
            
        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao converter JSON para objeto: " + e.getMessage(),
                    e
            );
        }

        throw new RuntimeException("Tipo de título não suportado");
    }

    // buscarDetalhesDaSerie(String nome);
    // buscarTemporadasDaSerie(String nome);
    // buscarEpisodiosDaTemporada(String nome, int temporada);
    // buscarAvaliacoesDoTitulo(String nome);
}
