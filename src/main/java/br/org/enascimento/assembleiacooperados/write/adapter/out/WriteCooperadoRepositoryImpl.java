package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.INVALID_DUPLICATE_DATA;

@Repository
public class WriteCooperadoRepositoryImpl implements WriteCooperadoRepository {

    private static final String NOME_FIELD = "nome";
    private static final String CPF_FIELD = "cpf";
    private static final String UUID_FIELD = "uuid";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private ReadCooperadoRepository repositoryRead;

    public WriteCooperadoRepositoryImpl(DataSource dataSource, ReadCooperadoRepository repositoryRead) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.repositoryRead = repositoryRead;
    }

    @Override
    public void create(Cooperado cooperado) {

        try {
            String sql = """
                    INSERT INTO cooperado(uuid, nome, cpf)
                    values (:uuid, :nome, :cpf)""";

            var parameters = new MapSqlParameterSource()
                    .addValue("uuid", cooperado.getUuid())
                    .addValue("nome", cooperado.getNome())
                    .addValue("cpf", cooperado.getCpf());

            jdbcTemplate.update(sql, parameters);

        } catch (DuplicateKeyException exception) {

            DuplicatedDataException duplicatedDataException = new DuplicatedDataException(INVALID_DUPLICATE_DATA, exception);
            var existentCooperado = repositoryRead.findByUuidOrCpf(cooperado.getUuid(), cooperado.getCpf()).get();

            if (existentCooperado.getCpf().equals(cooperado.getCpf())) {
                duplicatedDataException.addErrors("cpf", cooperado.getCpf());
            }

            if (existentCooperado.getUuid().equals(cooperado.getUuid())) {
                duplicatedDataException.addErrors("uuid", cooperado.getUuid());
            }

            throw duplicatedDataException;
        }
    }

    @Override
    public void update(Cooperado cooperado) {
        var sql = """
                UPDATE cooperado
                SET %s = :%s, 
                    %s = :%s
                WHERE %s = :%s
            """.formatted(NOME_FIELD, NOME_FIELD,
                          CPF_FIELD, CPF_FIELD,
                          UUID_FIELD, UUID_FIELD);

        var parameters = new MapSqlParameterSource()
                .addValue(NOME_FIELD, cooperado.getNome())
                .addValue(CPF_FIELD, cooperado.getCpf())
                .addValue(UUID_FIELD, cooperado.getUuid());

        jdbcTemplate.update(sql, parameters);
    }
}