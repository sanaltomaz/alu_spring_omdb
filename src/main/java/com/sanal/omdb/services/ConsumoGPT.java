package com.sanal.omdb.services;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

public class ConsumoGPT {
    public static String obterTraducao(String texto) {

        if (texto == null || texto.isBlank() || texto.equals("N/A")) {
            return texto;
        }

        try {
            OpenAIClient client = OpenAIOkHttpClient.fromEnv();

            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                    .model("gpt-5-mini")
                    .addUserMessage("Traduza para o português o seguinte texto, preservando nomes próprios: " + texto)
                    .build();

            ChatCompletion chatCompletion = client.chat()
                    .completions()
                    .create(params);

            return chatCompletion.choices()
                    .get(0)
                    .message()
                    .content()
                    .orElse(texto);
        } catch (Exception e) {
            System.err.println("Erro ao chamar o GPT para tradução: " + e.getMessage());
            return texto;
        }
    }
}

