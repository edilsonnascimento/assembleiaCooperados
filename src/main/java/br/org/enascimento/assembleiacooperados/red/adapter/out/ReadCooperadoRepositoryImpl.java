package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.CooperadoInDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReadCooperadoRepositoryImpl implements ReadCooperadoRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ReadCooperadoRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<CooperadoInDto> findAll() {
        var sql = "SELECT uuid, nome, cpf, created_at FROM cooperado ORDER BY created_at";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CooperadoInDto(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("nome"),
                        rs.getString("cpf"))
        );
    }

    @Override
    public Optional<Cooperado> findByUuid(UUID uuid) {

        var sql = """
                SELECT uuid, nome, cpf, created_at, updated_at
                FROM cooperado
                WHERE uuid = :uuid""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                var cooperado = new Cooperado();
                        cooperado.setUuid(UUID.fromString(resultSet.getString("uuid")));
                        cooperado.setNome(resultSet.getString("nome"));
                        cooperado.setCpf(resultSet.getString("cpf"));
                        cooperado.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                        cooperado.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                return Optional.of(cooperado);
            }
            return Optional.empty();
        });
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
                var cooperado = new Cooperado();
                        cooperado.setId(resultSet.getLong("id"));
                        cooperado.setUuid(UUID.fromString(resultSet.getString("uuid")));
                        cooperado.setNome(resultSet.getString("nome"));
                        cooperado.setCpf(resultSet.getString("cpf"));
                        cooperado.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                        cooperado.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                return Optional.of(cooperado);
            }
            return Optional.empty();
        });
    }
}