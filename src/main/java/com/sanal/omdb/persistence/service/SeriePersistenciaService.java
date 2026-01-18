package com.sanal.omdb.persistence.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanal.omdb.models.TipoTitulo;
import com.sanal.omdb.models.Titulo;
import com.sanal.omdb.persistence.entity.SerieEntity;
import com.sanal.omdb.persistence.mapper.TituloEntityMapper;
import com.sanal.omdb.persistence.repository.SerieRepository;

/**
 * Service responsável exclusivamente pela persistência de séries.
 *
 * Papel desta classe:
 * - Receber objetos de domínio já carregados
 * - Garantir que o tipo do título é SERIE
 * - Converter o domínio para entidade JPA
 * - Persistir os dados no banco
 *
 * NÃO faz:
 * - Busca de dados na OMDB
 * - Conversão de DTOs externos
 * - Lógica de negócio ou análise
 * - Decisão de fluxo da aplicação
 *
 * Observações:
 * - Método interno de suporte
 * - Não deve ser chamado fora deste service
 * - Executa dentro da transação do método chamador
 * - Operação atômica por temporada
 */
@Service
public class SeriePersistenciaService {
 
    private final SerieRepository serieRepository;
    private final TituloEntityMapper mapper;

    public SeriePersistenciaService(
        SerieRepository serieRepository,
        TituloEntityMapper mapper
    ) {
        this.serieRepository = serieRepository;
        this.mapper = mapper;
    }

    /**
     * Persiste uma série no banco de dados.
     *
     * Pré-condições:
     * - O título não pode ser nulo
     * - O tipo do título deve ser SERIE
     *
     * Responsabilidade:
     * - Validar o tipo do domínio
     * - Delegar a conversão para o mapper
     * - Persistir a entidade usando o repositório
     *
     * @param titulo objeto de domínio já carregado
     * @return entidade persistida
     */
    @Transactional
    public @NonNull SerieEntity salvarSerie(@NonNull Titulo titulo) {

        if (titulo.getTipo() != TipoTitulo.SERIE) {
            throw new IllegalStateException("Tentativa de persistir título que não é série");
        }

        SerieEntity entity = mapper.toSerieEntity(titulo);
        return serieRepository.save(entity);
    }
}
