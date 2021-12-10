package br.org.enascimento.assembleiacooperados.write.adapter.out;

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

    private NamedParameterJdbcTemplate jdbcTemplate;

    public WriteCooperadoRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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
            var existentCooperado = findByUuidOrCpf(cooperado.getUuid(), cooperado.getCpf()).get();

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
    public Optional<Cooperado> findByUuidOrCpf(UUID uuid, String cpf) {
        var sql = """
                SELECT id, uuid, nome, cpf, created_at, updated_at
                FROM cooperado
                WHERE uuid = :uuid OR cpf = :cpf""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid)
                .addValue("cpf", cpf);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                return Optional.of(new Cooperado().
                        setId(resultSet.getLong("id")).
                        setUuid(UUID.fromString(resultSet.getString("uuid"))).
                        setNome(resultSet.getString("nome")).
                        setCpf(resultSet.getString("cpf")).
                        setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime()).
                        setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                );
            }
            return Optional.empty();
        });
    }
}
