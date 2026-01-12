package com.sanal.omdb.persistence.mapper;

import com.sanal.omdb.models.TipoTitulo;
import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.persistence.entity.FilmeEntity;
import com.sanal.omdb.persistence.entity.TituloEntity;

/**
 * Mapper responsável por converter objetos de domínio (Titulo)
 * para entidades de persistência (TituloEntity e subclasses),
 * e vice-versa.
 *
 * Responsabilidade:
 * - Traduzir o modelo de domínio para o modelo relacional
 * - Isolar a camada de persistência do restante da aplicação
 *
 * NÃO faz:
 * - Persistência em banco
 * - Decisão de fluxo
 * - Criação de entidades incompletas fora do contrato
 * - Conversão de DTOs externos (OMDB)
 *
 * Observação importante:
 * - Este mapper conhece tanto o domínio quanto a persistência
 * - Ele é o ponto de acoplamento consciente entre esses dois mundos
 */
public class TituloEntityMapper {

    /**
     * Converte um Titulo de domínio em uma entidade de Filme.
     *
     * Pré-condições:
     * - O título deve ser do tipo FILME
     *
     * Responsabilidade:
     * - Copiar dados do domínio para a entidade
     * - Não definir ID (gerado pelo JPA)
     *
     * Observação:
     * - Séries e episódios terão métodos próprios
     */
    public FilmeEntity toFilmeEntity(Titulo titulo) {
        if (titulo == null) {
            return null;
        }

        if (titulo.getTipo() != TipoTitulo.FILME) {
            throw new IllegalArgumentException("Título não é um filme");
        }

        FilmeEntity entity = new FilmeEntity();
        entity.setTitulo(titulo.getTitulo());
        entity.setAvaliacao(titulo.getAvaliacao());
        entity.setDataLancamento(titulo.getDataLancamento());
        entity.setSinopse(titulo.getSinopse());
        entity.setTipo(titulo.getTipo());
        entity.setDuracao(titulo.getDuracao());

        return entity;
    }

    /**
     * Converte uma entidade persistida em um objeto de domínio.
     *
     * Responsabilidade:
     * - Reconstruir o domínio a partir do estado persistido
     *
     * Observação:
     * - A implementação depende do tipo concreto da entidade
     * - Será expandida conforme novas entidades forem criadas
     */
    public Titulo toDomain(TituloEntity entity) {
        if (entity == null) {
            return null;
        }

        // Implementação futura:
        // - if (entity instanceof FilmeEntity) { ... }
        // - if (entity instanceof SerieEntity) { ... }

        throw new UnsupportedOperationException(
            "Conversão de entidade para domínio ainda não implementada"
        );
    }

    /*
     * Métodos previstos para evolução:
     *
     * - toSerieEntity(Titulo titulo)
     * - toEpisodioEntity(Titulo titulo)
     * - toDomain(FilmeEntity entity)
     * - toDomain(SerieEntity entity)
     *
     * Esses métodos surgirão conforme a persistência evoluir.
     */
}
