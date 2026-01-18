package com.sanal.omdb.persistence.mapper;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.sanal.omdb.models.TipoTitulo;
import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.persistence.entity.FilmeEntity;
import com.sanal.omdb.persistence.entity.SerieEntity;
import com.sanal.omdb.persistence.entity.TituloEntity;

/**
 * Mapper responsável por converter objetos de domínio (Titulo)
 * para entidades de persistência (TituloEntity e suas especializações),
 * e vice-versa.
 *
 * Papel desta classe:
 * - Traduzir o modelo de domínio para o modelo relacional
 * - Centralizar a lógica de conversão entre camadas
 *
 * NÃO faz:
 * - Persistência em banco de dados
 * - Busca de informações externas
 * - Decisão de fluxo da aplicação
 * - Conversão de DTOs da OMDB
 *
 * Observação importante:
 * Este mapper é o ponto de acoplamento consciente entre
 * domínio e persistência. Todo o resto da aplicação
 * permanece isolado do JPA.
 */
@Component
public class TituloEntityMapper {

    /**
     * Converte um Titulo de domínio em uma entidade de Filme.
     *
     * Pré-condições:
     * - O título não pode ser nulo
     * - O tipo do título deve ser FILME
     *
     * Responsabilidade:
     * - Copiar os dados do domínio para a entidade
     * - Não definir o ID (gerado automaticamente pelo JPA)
     *
     * @param titulo objeto de domínio
     * @return entidade de filme pronta para persistência
     */
    @NonNull
    public FilmeEntity toFilmeEntity(@NonNull Titulo titulo) {

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
     * Converte um Titulo de domínio em uma entidade de Série.
     *
     * Pré-condições:
     * - O título não pode ser nulo
     * - O tipo do título deve ser SERIE
     *
     * Responsabilidade:
     * - Copiar os dados do domínio para a entidade
     * - Não definir o ID (gerado automaticamente pelo JPA)
     *
     * @param titulo objeto de domínio
     * @return entidade de série pronta para persistência
     */
    @NonNull
    public SerieEntity toSerieEntity(@NonNull Titulo titulo) {
        
        if (titulo.getTipo() != TipoTitulo.SERIE) {
            throw new IllegalArgumentException("Título não é uma série");
        }

        SerieEntity entity = new SerieEntity();
        entity.setTitulo(titulo.getTitulo());
        entity.setAvaliacao(titulo.getAvaliacao());
        entity.setDataLancamento(titulo.getDataLancamento());
        entity.setSinopse(titulo.getSinopse());
        entity.setTipo(titulo.getTipo());
        entity.setTotalTemporadas(titulo.getTemporadas());

        return entity;
    }

    /**
     * Converte uma entidade persistida em um objeto de domínio.
     *
     * Responsabilidade:
     * - Reconstruir o domínio a partir do estado armazenado
     *
     * Observação:
     * - A implementação depende do tipo concreto da entidade
     * - Será expandida conforme novas entidades forem criadas
     *
     * @param entity entidade persistida
     * @return objeto de domínio correspondente
     */
    public Titulo toDomain(TituloEntity entity) {
        if (entity == null) {
            return null;
        }

        // Implementação futura:
        // if (entity instanceof FilmeEntity) { ... }
        // if (entity instanceof SerieEntity) { ... }

        throw new UnsupportedOperationException(
            "Conversão de entidade para domínio ainda não implementada"
        );
    }

    /*
     * Evolução prevista:
     *
     * - toEpisodioEntity(...)
     * - toDomain(FilmeEntity)
     * - toDomain(SerieEntity)
     *
     * Esses métodos serão adicionados conforme
     * a camada de persistência evoluir.
     */
}
