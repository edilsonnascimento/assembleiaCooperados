package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

public class WriteCooperadoRepositoryImpl implements WriteCooperadoRepository {

    public WriteCooperadoRepositoryImpl(DataSource dataSource) {
    }

    @Override
    public void create(Cooperado capture) {

    }

    @Override
    public Optional<Cooperado> findByUuid(UUID uuid) {
        return Optional.empty();
    }
}
