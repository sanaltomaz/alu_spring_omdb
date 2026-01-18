package com.sanal.omdb.omdb;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanal.omdb.dto.omdb.OmdbEpisodioDto;
import com.sanal.omdb.dto.omdb.OmdbFilmeDto;
import com.sanal.omdb.dto.omdb.OmdbSerieDto;
import com.sanal.omdb.dto.omdb.OmdbTemporadaDto;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Concentra tudo que é específico da API do OMDB.
 *
 * Responsabilidades:
 * - Montagem de URLs da OMDB
 * - Chamada HTTP
 * - Tratamento básico de erros externos
 * - Conversão de JSON para DTOs externos
 *
 * Não contém:
 * - Regras de negócio
 * - Lógica de análise
 * - Decisão de fluxo da aplicação
 * 
 *  * Limitações conhecidas:
 * - Configuração via dotenv (a ser migrada para injeção de configuração)
 * - Identificação de tipo baseada em inspeção de JSON bruto
 * - HttpClient instanciado internamente
 */
@Component
public class OmdbClient {

    private final Dotenv dotenv = Dotenv.load();
    private final String endereco = dotenv.get("ENDERECO");
    private final String apiKey = dotenv.get("API_KEY");

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Executa a chamada HTTP para a API da OMDB e retorna o JSON bruto.
     */
    private String consumirApiOmdb(String endereco) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao consumir a API OMDB", e);
        }
    }

    /**
     * Busca os dados brutos de um título pelo nome.
     */
    private String buscarTituloPorNome(String nome) {
        String url = endereco + nome.replace(" ", "+") + apiKey;
        return consumirApiOmdb(url);
    }

    /**
     * Identifica o tipo técnico do título com base no JSON retornado pela OMDB.
     */
    private Class<?> identificarTipoDoTitulo(String json) {
        if (json.contains("\"Type\":\"movie\"")) {
            return OmdbFilmeDto.class;
        }
        if (json.contains("\"Type\":\"series\"")) {
            return OmdbSerieDto.class;
        }
        if (json.contains("\"Episode\":")) {
            return OmdbEpisodioDto.class;
        }
        throw new RuntimeException("Tipo de título não suportado pela OMDB");
    }

    /**
     * Busca um título genérico na OMDB e converte para o DTO externo apropriado.
     *
     * Retorno genérico é intencional e será refinado futuramente.
     */
    public Object buscarTitulo(String nome) {
        String json = buscarTituloPorNome(nome);
        Class<?> tipo = identificarTipoDoTitulo(json);

        try {
            return mapper.readValue(json, tipo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON da OMDB", e);
        }
    }

    /**
     * Busca os dados base de uma série (sem episódios).
     *
     * Responsabilidade:
     * - Consultar a OMDB pelo nome da série
     * - Retornar apenas os metadados da série
     */
    public OmdbSerieDto buscarSerie(String nomeSerie) {
        String json = buscarTituloPorNome(nomeSerie);

        try {
            return mapper.readValue(json, OmdbSerieDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter série da OMDB", e);
        }
    }

    /**
     * Busca uma temporada específica de uma série na OMDB.
     *
     * Responsabilidade:
     * - Montar a URL com parâmetro de temporada
     * - Retornar os episódios da temporada
     *
     * Observação:
     * - Não realiza loop de temporadas
     * - Não conhece regra de negócio
     */
    public OmdbTemporadaDto buscarTemporada(String nomeSerie, int temporada) {
        String url = endereco
                + nomeSerie.replace(" ", "+")
                + "&Season=" + temporada
                + apiKey;

        String json = consumirApiOmdb(url);

        try {
            return mapper.readValue(json, OmdbTemporadaDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter temporada da OMDB", e);
        }
    }
}
