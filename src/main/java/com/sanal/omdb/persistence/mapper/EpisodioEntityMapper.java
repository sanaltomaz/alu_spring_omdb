package com.sanal.omdb.persistence.mapper;

import org.springframework.stereotype.Component;

import com.sanal.omdb.dto.omdb.OmdbEpisodioDto;
import com.sanal.omdb.persistence.entity.EpisodioEntity;
import com.sanal.omdb.persistence.entity.SerieEntity;

/**
 * Mapper responsável por converter dados de episódios
 * para entidades de persistência.
 *
 * Responsabilidades:
 * - Converter dados vindos da OMDB em EpisodioEntity
 * - Garantir vínculo com uma SerieEntity já persistida
 *
 * NÃO faz:
 * - Persistência em banco
 * - Busca de série
 * - Decisão de fluxo
 * - Validação de regras de negócio (apenas invariantes estruturais)
 *
 * Observações:
 * - Episódios sempre dependem de uma série existente
 * - Este mapper não cria entidades órfãs
 */
@Component
public class EpisodioEntityMapper {

    /**
     * Converte dados de um episódio em uma entidade persistível.
     *
     * Pré-condições:
     * - Série já persistida
     * - Dados do episódio já carregados da OMDB
     *
     * @param episodioDto dados do episódio
     * @param serie série persistida
     * @param numeroTemporada número da temporada
     * @return EpisodioEntity pronta para persistência
     */
    public EpisodioEntity toEntity(
        OmdbEpisodioDto episodioDto,
        SerieEntity serie,
        Integer numeroTemporada
    ) {
        
        if (episodioDto == null) {
            throw new IllegalArgumentException("Dados de episódio não podem ser nulo");
        }

        if (episodioDto.episodio() == null) {
            throw new IllegalArgumentException("Número do episódio é obrigatório");
        }

        if (serie == null) {
            throw new IllegalArgumentException("Série não pode ser nula");
        }

        EpisodioEntity entity = new EpisodioEntity();
        entity.setTitulo(episodioDto.titulo());
        entity.setNumeroEpisodio(episodioDto.episodio());
        entity.setNumeroTemporada(numeroTemporada);
        entity.setAvaliacao(parseAvaliacao(episodioDto.avaliacao()));
        entity.setSerie(serie); 

        return entity;
    }

    private Double parseAvaliacao(String avaliacao) {
        if (avaliacao == null) {
            return null;
        }

        String valor = avaliacao.trim();

        if (valor.isEmpty() || valor.equalsIgnoreCase("N/A")) {
            return null;
        }

        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
