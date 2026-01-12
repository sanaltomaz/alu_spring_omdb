package com.sanal.omdb.persistence.mapper;

import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.persistence.entity.TituloEntity;

public class TituloEntityMapper {

    public TituloEntity toEntity(Titulo titulo) {
        if (titulo == null) {
            return null;
        }

        // Decidir Filme / Serie
        throw new UnsupportedOperationException("Mapeamento ainda não implementado");
    }

    public Titulo toDomain(TituloEntity entity) {
        if (entity == null) {
            return null;
        }

        // Reconstrói o domínio
        throw new UnsupportedOperationException("Mapeamento ainda não implementado");
    }
}
