package com.sanal.omdb.models;

/**
 * Enum que representa os tipos de título do domínio.
 *
 * Utilizado para:
 * - Definir o comportamento do objeto Titulo
 * - Controlar regras de apresentação
 * - Distinguir responsabilidades no domínio
 *
 * Este enum é independente de APIs externas
 * e não representa diretamente os valores da OMDB.
 */
public enum TipoTitulo {
    FILME,
    SERIE,
    EPISODIO
}
