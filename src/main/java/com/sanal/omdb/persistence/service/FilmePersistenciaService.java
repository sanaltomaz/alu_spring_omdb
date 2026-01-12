package com.sanal.omdb.persistence.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanal.omdb.models.TipoTitulo;
import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.persistence.entity.FilmeEntity;
import com.sanal.omdb.persistence.mapper.TituloEntityMapper;
import com.sanal.omdb.persistence.repository.FilmeRepository;

/**
 * Service responsável exclusivamente pela persistência de filmes.
 *
 * Responsabilidades:
 * - Validar se o título é do tipo FILME
 * - Converter o domínio para entidade JPA
 * - Persistir no banco de dados
 *
 * NÃO faz:
 * - Busca na OMDB
 * - Lógica de negócio
 * - Análises
 * - Interação com usuário
 */
@Service
public class FilmePersistenciaService {

    private final FilmeRepository filmeRepository;
    private final TituloEntityMapper mapper;

    public FilmePersistenciaService(
        FilmeRepository filmeRepository,
        TituloEntityMapper mapper
    ) {
        this.filmeRepository = filmeRepository;
        this.mapper = mapper;
    }

    /**
     * Persiste um filme no banco de dados.
     *
     * @param titulo domínio já carregado
     * @return entidade persistida
     */
    @Transactional
    public @NonNull FilmeEntity salvarFilme(@NonNull Titulo titulo) {

        if (titulo.getTipo() != TipoTitulo.FILME) {
            throw new IllegalStateException("Tentativa de persistir título que não é filme");
        }

        FilmeEntity entity = mapper.toFilmeEntity(titulo);
        return filmeRepository.save(entity);
    }
}
