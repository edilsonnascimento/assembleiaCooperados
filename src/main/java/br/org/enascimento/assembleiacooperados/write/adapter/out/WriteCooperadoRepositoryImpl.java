package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCooperadoRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WriteCooperadoRepositoryImpl implements WriteCooperadoRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public WriteCooperadoRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void create(Cooperado cooperado) {
            String sql = """
                    INSERT INTO cooperado(uuid, nome, cpf)
                    values (:uuid, :nome, :cpf)""";

            var parameters = new MapSqlParameterSource()
                    .addValue("uuid", cooperado.getUuid())
                    .addValue("nome", cooperado.getNome())
                    .addValue("cpf", cooperado.getCpf());

            jdbcTemplate.update(sql, parameters);
    }

    @Override
    public Optional<Cooperado> findByUuid(UUID uuid) {
        var sql = """
                SELECT id, uuid, nome, cpf, created_at, updated_at
                FROM cooperado
                WHERE uuid = :uuid""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

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
